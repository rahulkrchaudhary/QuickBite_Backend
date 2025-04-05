package com.rahul.controller;

import com.rahul.model.Payment;
import com.rahul.model.User;
import com.rahul.service.PaymentService;
import com.rahul.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private UserService userService;
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/payment")
    public ResponseEntity<List<Payment>> getAllUserPayment(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
//        System.out.println("--------------------" + user.getId());
        List<Payment> paymentList = paymentService.findPaymentByUser(user);
//        System.out.println("---------size"+ paymentList.size());
//        System.out.println("----------------we got list"+paymentList);
        return new ResponseEntity<>(paymentList, HttpStatus.OK);
    }


}
