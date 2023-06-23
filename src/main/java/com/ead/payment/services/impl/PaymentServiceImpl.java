package com.ead.payment.services.impl;

import com.ead.payment.dtos.PaymentRequestDTO;
import com.ead.payment.enums.PaymentControl;
import com.ead.payment.models.CreditCardModel;
import com.ead.payment.models.PaymentModel;
import com.ead.payment.models.UserModel;
import com.ead.payment.repositories.CreditCardRepository;
import com.ead.payment.repositories.PaymentRepository;
import com.ead.payment.services.PaymentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

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

        return payment;
    }

    @Override
    public Optional<PaymentModel> findLastPaymentByUser(UserModel userModel) {
        return paymentRepository.findTopByUserOrderByPaymentRequestDateDesc(userModel);
    }
}
