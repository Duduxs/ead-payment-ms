package com.ead.payment.services.impl;

import com.ead.payment.boundaries.PaymentBoundaryMock;
import com.ead.payment.dtos.PaymentCommandDTO;
import com.ead.payment.dtos.PaymentRequestDTO;
import com.ead.payment.enums.PaymentControl;
import com.ead.payment.enums.PaymentStatus;
import com.ead.payment.models.CreditCardModel;
import com.ead.payment.models.PaymentModel;
import com.ead.payment.models.UserModel;
import com.ead.payment.producers.PaymentCommandPublisher;
import com.ead.payment.producers.PaymentEventPublisher;
import com.ead.payment.repositories.CreditCardRepository;
import com.ead.payment.repositories.PaymentRepository;
import com.ead.payment.repositories.UserRepository;
import com.ead.payment.services.PaymentService;
import org.apache.logging.log4j.jul.LogManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    PaymentCommandPublisher paymentCommandPublisher;

    @Autowired
    PaymentEventPublisher paymentEventPublisher;

    PaymentBoundaryMock paymentBoundary = new PaymentBoundaryMock();

    Logger logger = new LogManager().getLogger(PaymentServiceImpl.class.getName());

    @Override
    @Transactional
    public PaymentModel requestPayment(PaymentRequestDTO dto, UserModel userModelOptional) {

        var creditCardModel = new CreditCardModel();
        var creditCardModelOptional = creditCardRepository.findByUser(userModelOptional);

        if (creditCardModelOptional.isPresent()) {
            creditCardModel = creditCardModelOptional.get();
        }

        BeanUtils.copyProperties(dto, creditCardModel);
        creditCardModel.setUser(userModelOptional);
        creditCardRepository.save(creditCardModel);

        var payment = new PaymentModel();
        payment.setPaymentControl(PaymentControl.REQUESTED);
        payment.setPaymentRequestDate(LocalDateTime.now(ZoneId.of("UTC")));
        payment.setPaymentExpirationDate(LocalDateTime.now(ZoneId.of("UTC")).plusDays(30));
        payment.setLastDigitsCreditCard(dto.getCreditCardNumber().substring(dto.getCreditCardNumber().length() - 4));
        payment.setUser(userModelOptional);
        paymentRepository.save(payment);

        try {
            var paymentCommandDTO = new PaymentCommandDTO();
            paymentCommandDTO.setUserId(userModelOptional.getUserId());
            paymentCommandDTO.setPaymentId(payment.getPaymentId());
            paymentCommandDTO.setCardId(creditCardModel.getCardId());
            paymentCommandPublisher.publishPaymentCommand(paymentCommandDTO);
        } catch (Exception e) {
            logger.warning("Error sending payment command!");
        }

        return payment;
    }

    @Override
    public Optional<PaymentModel> findLastPaymentByUser(UserModel userModel) {
        return paymentRepository.findTopByUserOrderByPaymentRequestDateDesc(userModel);
    }

    @Override
    public Page<PaymentModel> getAllPayments(UUID userId, Pageable pageable) {
        return paymentRepository.findAllBy(userId, pageable);
    }

    @Override
    public Optional<PaymentModel> findPaymentByUser(UUID userId, UUID paymentId) {
        return paymentRepository.findByUser(userId, paymentId);
    }

    @Override
    @Transactional
    public void makePayment(PaymentCommandDTO dto) {

        var payment = paymentRepository.findById(dto.getPaymentId()).get();
        var user = userRepository.findById(dto.getUserId()).get();

        var status = paymentBoundary.pay();

        if (status == PaymentControl.EFFECTED) {
            payment.setPaymentControl(PaymentControl.EFFECTED);
            payment.setPaymentMessage("Payment effected with success!");
            payment.setPaymentCompletionDate(LocalDateTime.now(ZoneId.of("UTC")));

            user.setPaymentStatus(PaymentStatus.PAYING);
            user.setLastPaymentDate(LocalDateTime.now(ZoneId.of("UTC")));
            user.setPaymentExpirationDate(LocalDateTime.now(ZoneId.of("UTC")));
            if (user.getFirstPaymentDate() == null) {
                user.setFirstPaymentDate(LocalDateTime.now(ZoneId.of("UTC")));
            }

        } else if (status == PaymentControl.ERROR) {
            payment.setPaymentControl(PaymentControl.ERROR);
            payment.setPaymentMessage("Payment with error!");
            user.setPaymentStatus(PaymentStatus.DEBTOR);
        } else if (status == PaymentControl.REFUSED) {
            payment.setPaymentControl(PaymentControl.REFUSED);
            payment.setPaymentMessage("Payment refused!");
            user.setPaymentStatus(PaymentStatus.DEBTOR);
        }

        paymentRepository.save(payment);
        userRepository.save(user);

        if (payment.getPaymentControl().equals(PaymentControl.EFFECTED) || payment.getPaymentControl().equals(PaymentControl.REFUSED)) {
            paymentEventPublisher.publishEventCommand(payment.convertToDTO());
        }
    }
}
