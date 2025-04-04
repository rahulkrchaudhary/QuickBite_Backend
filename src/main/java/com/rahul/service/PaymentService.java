package com.rahul.service;

import com.rahul.model.Order;
import com.rahul.model.Payment;
import com.rahul.model.User;
import com.rahul.response.PaymentResponse;

import java.util.List;

public interface PaymentService {

    public PaymentResponse createPaymentLink(Order order);

    public Payment findPaymentByOrderId(Long orderId) throws Exception;

    public List<Payment> findPaymentByUser(User user);
}
