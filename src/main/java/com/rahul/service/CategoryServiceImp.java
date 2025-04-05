package com.rahul.service;

import com.rahul.model.Category;
import com.rahul.model.Restaurant;
import com.rahul.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService{

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(String name, Long userId) throws Exception{
        Restaurant restaurant=restaurantService.getRestaurantByUserId(userId);
        Category category=new Category();
        category.setName(name);
        category.setRestaurant(restaurant);
        return categoryRepository.save(category);

    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long id) throws Exception {
//        Restaurant restaurant=restaurantService.getRestaurantByUserId(id);
        Restaurant restaurant=restaurantService.findRestaurantById(id);
        return categoryRepository.findByRestaurantId(id);
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> optionalCategory=categoryRepository.findById(id);
        if(optionalCategory==null){
            throw new Exception("Category not found");
        }
        return optionalCategory.get();
    }
}
