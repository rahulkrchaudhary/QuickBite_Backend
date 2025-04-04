package com.rahul.service;

import com.rahul.dto.RestaurantDto;
import com.rahul.model.Restaurant;
import com.rahul.model.User;
import com.rahul.request.CreateRestaurantRequest;
import org.springframework.stereotype.Component;

import java.util.List;


public interface RestaurantService {
    public Restaurant creatRestaurant(CreateRestaurantRequest req, User user);

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurant();

    public List<Restaurant> searchRestaurant(String keyword);

    public Restaurant findRestaurantById(Long id) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long id) throws Exception;

    public List<Restaurant> searchRestaurantBYName(String keyword);
}
