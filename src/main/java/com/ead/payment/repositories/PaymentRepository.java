package com.ead.payment.repositories;

import com.ead.payment.models.PaymentModel;
import com.ead.payment.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentModel, UUID>, JpaSpecificationExecutor<PaymentModel> {

    @Query(value = "SELECT * FROM payment p where p.user_user_id = :userId", nativeQuery = true)
    Page<PaymentModel> findAllBy(UUID userId, Pageable pageable);

    @Query(value = "SELECT * FROM payment p where p.user_user_id = :userId and p.payment_id = :paymentId", nativeQuery = true)
    Optional<PaymentModel> findByUser(UUID userId, UUID paymentId);

    Optional<PaymentModel> findTopByUserOrderByPaymentRequestDateDesc(UserModel userModel);

}
