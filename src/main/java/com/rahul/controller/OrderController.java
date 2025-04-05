package com.rahul.controller;

import com.rahul.model.CartItem;
import com.rahul.model.Order;
import com.rahul.model.User;
import com.rahul.request.AddCartItemRequest;
import com.rahul.request.OrderRequest;
import com.rahul.response.PaymentResponse;
import com.rahul.service.OrderService;
import com.rahul.service.PaymentService;
import com.rahul.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/order")
    public ResponseEntity<PaymentResponse> createOrder(@RequestBody OrderRequest req,
                                                       @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        PaymentResponse paymentResponse=orderService.createOrder(req, user);
        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        List<Order> order=orderService.getUsersOrder(user.getId());
        return new ResponseEntity<>(order, HttpStatus.OK);
    }




}
