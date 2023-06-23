package com.ead.payment.services;


import com.ead.payment.dtos.PaymentRequestDTO;
import com.ead.payment.models.PaymentModel;
import com.ead.payment.models.UserModel;

import java.util.Optional;

public interface PaymentService {

    PaymentModel requestPayment(PaymentRequestDTO dto, UserModel userModelOptional);

    Optional<PaymentModel> findLastPaymentByUser(UserModel userModel);
}
