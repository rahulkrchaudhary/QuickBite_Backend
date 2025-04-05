package com.rahul.service;

import com.rahul.model.Category;
import com.rahul.model.Food;
import com.rahul.model.Restaurant;
import com.rahul.repository.FoodRepository;
import com.rahul.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImp implements FoodService{

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
        Food food= new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setImages(req.getImages());
        food.setName(req.getName());
        food.setPrice((long)req.getPrice());
        food.setIngredients(req.getIngredients());
        food.setSeasonal(req.isSeasonal());
        food.setVegetarian(req.isVegetarian());
        food.setCreationDate(new Date());

        Food savedFood=foodRepository.save(food);
        restaurant.getFoods().add(savedFood);
        return savedFood;

    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food=findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);
//        foodRepository.delete(food);
    }

    @Override
    public List<Food> getRestaurantFood(Long restaurantId,
                                        boolean isVegetarian,
                                        boolean isNonveg,
                                        boolean isSeasonal,
                                        String foodCategory) {
        List<Food> foods=foodRepository.findByRestaurantId(restaurantId);
        if(isVegetarian){
            foods=filterByVegetarian(foods, isVegetarian);
        }
        if(isNonveg){
            foods=filterByNonveg(foods, isNonveg);
        }
        if(isSeasonal){
            foods=filterBySeasonal(foods, isSeasonal);
        }
        if(foodCategory!=null && !foodCategory.equals("")){
            foods=filterByCategory(foods, foodCategory);
        }

        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food ->{
            if(food.getFoodCategory()!=null){
                return food.getFoodCategory().getName().equals(foodCategory);
            }else {
                return false;
            }
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal()==isSeasonal).collect(Collectors.toList());

    }

    private List<Food> filterByNonveg(List<Food> foods, boolean isNonveg) {
        return foods.stream().filter(food -> food.isVegetarian()==false).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian()==isVegetarian).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> opt=foodRepository.findById(foodId);
        if(opt.isEmpty()){
            throw new Exception("food not exists....");
        }
        return opt.get();
    }

    @Override
    public Food updateAvailability(Long foodId) throws Exception {
        Food food=findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        foodRepository.save(food);
        return food;

    }
}
