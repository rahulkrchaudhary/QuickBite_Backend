package com.rahul.controller;

import com.rahul.model.Food;
import com.rahul.model.Restaurant;
import com.rahul.model.User;
import com.rahul.request.CreateFoodRequest;
import com.rahul.service.FoodService;
import com.rahul.service.RestaurantService;
import com.rahul.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name,
                                                @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        List<Food> food=foodService.searchFood(name);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(@RequestParam (required = false) boolean vegetarian,
                                                 @RequestParam (required = false) boolean seasonal,
                                                 @RequestParam (required = false) boolean nonveg,
                                                 @RequestParam (required = false) String food_category,
                                                 @PathVariable Long restaurantId,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        List<Food> food=foodService.getRestaurantFood(restaurantId, vegetarian, nonveg, seasonal, food_category);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }


}
