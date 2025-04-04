package com.rahul.service;

import com.rahul.model.Category;
import com.rahul.model.Food;
import com.rahul.model.Restaurant;
import com.rahul.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    public void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantFood(Long restaurantId,
                                        boolean isVegetarian,
                                        boolean isNonveg,
                                        boolean isSeasonal,
                                        String foodCategory);

    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long foodId) throws Exception;

    public Food updateAvailability(Long foodId) throws Exception;
}
