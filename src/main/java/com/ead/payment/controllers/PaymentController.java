package com.ead.payment.controllers;

import com.ead.payment.dtos.PaymentRequestDTO;
import com.ead.payment.enums.PaymentControl;
import com.ead.payment.models.PaymentModel;
import com.ead.payment.models.UserModel;
import com.ead.payment.services.PaymentService;
import com.ead.payment.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class PaymentController {

    @Autowired
    UserService userService;

    @Autowired
    PaymentService paymentService;

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/users/{userId}/payments/{paymentId}")
    public ResponseEntity<Object> getOnePayment(@PathVariable UUID userId, @PathVariable UUID paymentId) {
        var payment = paymentService.findPaymentByUser(userId, paymentId);

        if(payment.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found for this user.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(payment.get());

    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/users/{userId}/payments")
    public ResponseEntity<Page<PaymentModel>> getAllPayments(
            @PathVariable UUID userId,
            @PageableDefault(page = 0, size = 10, sort = "payment_id", direction = DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(paymentService.getAllPayments(userId, pageable));
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/users/{userId}/payments")
    public ResponseEntity<Object> requestPayment(
            @PathVariable(value = "userId") UUID userId,
            @RequestBody @Valid PaymentRequestDTO dto
    ) {
        Optional<UserModel> userModelOptional = userService.findById(userId);

        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Optional<PaymentModel> paymentModelOptional = paymentService.findLastPaymentByUser(userModelOptional.get());

        if (paymentModelOptional.isPresent()) {
            if (paymentModelOptional.get().getPaymentControl().equals(PaymentControl.REQUESTED)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Payment already requested.");
            }
            if (paymentModelOptional.get().getPaymentControl().equals(PaymentControl.EFFECTED) && paymentModelOptional.get().getPaymentExpirationDate().isAfter(LocalDateTime.now(ZoneId.of("UTC")))) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Payment already made.");

            }

        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(paymentService.requestPayment(dto, userModelOptional.get()));
    }
}
