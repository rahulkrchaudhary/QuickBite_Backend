package com.rahul.controller;

import com.rahul.model.Order;
import com.rahul.model.Payment;
import com.rahul.repository.OrderRepository;
import com.rahul.repository.PaymentRepository;
import com.rahul.service.OrderService;
import com.rahul.service.PaymentService;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//https://146e-103-211-17-255.ngrok-free.app


@RestController
@RequestMapping("/api/stripe")
public class StripeWebhookController {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, "whsec_9HBGZHuXXe55gEMTYwRLweHlkHl2gfpp");

            if ("checkout.session.completed".equals(event.getType())) {
                System.out.println("------------------------------------payment successfull ");
                Session session = (Session) event.getData().getObject();
                Optional<Payment> paymentOpt = paymentRepository.findByPaymentId(session.getId());

                if (paymentOpt.isPresent()) {
                    Payment payment = paymentOpt.get();
                    payment.setPaymentStatus("SUCCESS");
                    paymentRepository.save(payment);

                    Order order = orderService.findOrderById(payment.getOrderId());
                    order.setPayment(payment);
                    orderRepository.save(order);

                }
            } else if ("payment_intent.payment_failed".equals(event.getType())) {
                System.out.println("-------------------------payment failed");
                PaymentIntent paymentIntent = (PaymentIntent) event.getData().getObject();
                Optional<Payment> paymentOpt = paymentRepository.findByPaymentId(paymentIntent.getId());

                if (paymentOpt.isPresent()) {
                    Payment payment = paymentOpt.get();
                    payment.setPaymentStatus("FAILED");
                    paymentRepository.save(payment);

                    orderRepository.deleteById(payment.getOrderId());
                }
            }
            return ResponseEntity.ok("Webhook received");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Webhook error");
        }
    }
}
