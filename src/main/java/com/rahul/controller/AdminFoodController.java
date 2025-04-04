package com.rahul.controller;

import com.rahul.model.Food;
import com.rahul.model.Restaurant;
import com.rahul.model.User;
import com.rahul.request.CreateFoodRequest;
import com.rahul.response.MessageResponse;
import com.rahul.service.FoodService;
import com.rahul.service.RestaurantService;
import com.rahul.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
//        Restaurant restaurant=restaurantService.getRestaurantByUserId(user.getId());
        Restaurant restaurant=restaurantService.findRestaurantById(req.getRestaurantId());
        Food food=foodService.createFood(req, req.getCategory(), restaurant);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        foodService.deleteFood(id);
        MessageResponse messageResponse= new MessageResponse();
        messageResponse.setMessage("food deleted sucessfully...");
        return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvailabilityStatus(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Food food=foodService.updateAvailability(id);
        return new ResponseEntity<>(food, HttpStatus.CREATED);

    }

}
