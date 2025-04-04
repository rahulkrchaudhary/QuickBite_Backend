package com.rahul.service;

import com.rahul.model.*;
import com.rahul.repository.*;
import com.rahul.request.OrderRequest;
import com.rahul.response.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private CartService cartService;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private AddressService addressService;


    @Override
    public PaymentResponse createOrder(OrderRequest orderRequest, User user)  throws Exception{

        Address shippingAddress = addressService.findShippingAddress(orderRequest.getDeliveryAddress(), user);

        Restaurant restaurant=restaurantService.findRestaurantById(orderRequest.getRestaurantId());

        Order createdOrder=new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(new Date());
//        createdOrder.setCreatedAt();
        createdOrder.setOrderStatus("Pending");
        createdOrder.setDeliveryAddress(shippingAddress);
        createdOrder.setRestaurant(restaurant);

        Cart cart=cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItems= new ArrayList<>();

        for(CartItem cartItem: cart.getItems()){
            OrderItem orderItem= new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getFood().getPrice()*cartItem.getQuantity());

            OrderItem sacedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(sacedOrderItem);
        }

        Long totalPrice=cartService.calculateTotal(cart);

        createdOrder.setItems(orderItems);
        createdOrder.setTotalAmount(totalPrice+21+5+33);
        createdOrder.setTotalItem(orderItems.size());

        Order savedOrder=orderRepository.save(createdOrder);

        restaurant.getOrders().add(savedOrder);
        restaurantRepository.save(restaurant);

        //payment logic
        PaymentResponse paymentResponse = paymentService.createPaymentLink(createdOrder);
        return paymentResponse;

    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order=findOrderById(orderId);
        if(orderStatus.equals("OUT_FOR_DELIVERY")
                    || orderStatus.equals("DELIVERED")
                    || orderStatus.equals("COMPLETED")
                    || orderStatus.equals("PENDING")){
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
            return order;
        }
        throw new Exception("please select a valid order status");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order=findOrderById(orderId);
        orderRepository.deleteById(orderId);

    }

    @Override
    public List<Order> getUsersOrder(Long userId) throws Exception {

        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders=orderRepository.findByRestaurantId(restaurantId);
        if(orderStatus!=null){
            orders=orders.stream().filter(order->order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> order=orderRepository.findById(orderId);
        if(order.isEmpty()){
            throw new Exception("order not found");
        }
        return order.get();
    }
}
