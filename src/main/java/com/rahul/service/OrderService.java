package com.rahul.service;

import com.rahul.model.Order;
import com.rahul.model.User;
import com.rahul.request.OrderRequest;
import com.rahul.response.PaymentResponse;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderService {

    public PaymentResponse createOrder(OrderRequest orderRequest, User user) throws Exception;

    public Order updateOrder(Long OrderId, String orderStatus) throws Exception;

    public void cancelOrder(Long orderId) throws Exception;

    public List<Order> getUsersOrder(Long userId) throws Exception;

    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception;

    public Order findOrderById(Long orderId) throws Exception;

}
