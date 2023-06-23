package com.ead.payment.services;


import com.ead.payment.dtos.PaymentCommandDTO;
import com.ead.payment.dtos.PaymentRequestDTO;
import com.ead.payment.models.PaymentModel;
import com.ead.payment.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface PaymentService {

    PaymentModel requestPayment(PaymentRequestDTO dto, UserModel userModelOptional);

    Optional<PaymentModel> findLastPaymentByUser(UserModel userModel);

    Page<PaymentModel> getAllPayments(UUID userId, Pageable pageable);

    Optional<PaymentModel> findPaymentByUser(UUID userId, UUID paymentId);

    void makePayment(PaymentCommandDTO dto);
}
