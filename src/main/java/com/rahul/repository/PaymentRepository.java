package com.rahul.repository;

import com.rahul.model.Payment;
import com.rahul.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPaymentId(String paymentId);
    Optional<Payment> findByOrderId(Long id);
    List<Payment> findByUser(User user);
}
