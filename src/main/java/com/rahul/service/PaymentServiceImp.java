package com.rahul.service;

import com.rahul.model.Order;
import com.rahul.model.Payment;
import com.rahul.model.User;
import com.rahul.repository.OrderRepository;
import com.rahul.repository.PaymentRepository;
import com.rahul.response.PaymentResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImp implements PaymentService{

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Value("${fronted.url}")
    private String frontedUrl;

    @Override
    public PaymentResponse createPaymentLink(Order order) {
        Stripe.apiKey=stripeSecretKey;
        SessionCreateParams params=SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(frontedUrl+"/payment/success/"+order.getId())
                .setCancelUrl(frontedUrl+"/payment/fail")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("INR")
                                .setUnitAmount((long) order.getTotalAmount()*100)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("quick bites")
                                        .build())
                                .build()
                        )
                        .build()
                )
                .build();

        Session session=null;
        try{
            session=Session.create(params);
        }catch (StripeException ex) {
            throw new RuntimeException("Failed to create payment session");
//            System.out.println(ex.getMessage());
        }

        Payment payment = new Payment();

        payment.setOrderId(order.getId());
        payment.setPaymentId(session.getId());
        payment.setAmount(order.getTotalAmount()*100);
        payment.setCurrency("INR");
        payment.setPaymentStatus("PENDING");
        payment.setUser(order.getCustomer());
        paymentRepository.save(payment);

//        order.setPayment(payment);
//        orderRepository.save(order);


        PaymentResponse paymentResponse=new PaymentResponse();
        paymentResponse.setPayment_url(session.getUrl());
        return paymentResponse;
    }

    @Override
    public Payment findPaymentByOrderId(Long orderId) throws Exception {
        Optional<Payment> payment = paymentRepository.findByOrderId(orderId);
        if(payment.isEmpty()){
            throw new Exception("Payment not found");
        }
        return payment.get();
    }

    @Override
    public List<Payment> findPaymentByUser(User user) {
        return paymentRepository.findByUser(user);
    }


}
