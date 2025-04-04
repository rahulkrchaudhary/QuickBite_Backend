package com.rahul.service;

import com.rahul.model.IngredientsCategory;
import com.rahul.model.IngredientsItem;
import com.rahul.model.Restaurant;
import com.rahul.repository.IngredientsCategoryRepository;
import com.rahul.repository.IngredientsItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientsServiceImp implements IngredientsService{

    @Autowired
    private IngredientsItemRepository ingredientsItemRepository;
    @Autowired
    private IngredientsCategoryRepository ingredientsCategoryRepository;
    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientsCategory createIngredientsCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
        IngredientsCategory ingredientsCategory=new IngredientsCategory();
        ingredientsCategory.setName(name);
        ingredientsCategory.setRestaurant(restaurant);
        return ingredientsCategoryRepository.save(ingredientsCategory);
    }

    @Override
    public IngredientsCategory findIngredientsCategoryById(Long id) throws Exception {
        Optional<IngredientsCategory>opt=ingredientsCategoryRepository.findById(id);
        if(opt.isEmpty()){
            throw new Exception("Ingredients category not found");
        }
        return opt.get();
    }

    @Override
    public List<IngredientsCategory> findIngredientsCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientsCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant= restaurantService.findRestaurantById(restaurantId);
        IngredientsCategory ingredientsCategory=findIngredientsCategoryById(categoryId);

        IngredientsItem ingredientsItem= new IngredientsItem();
        ingredientsItem.setName(ingredientName);
        ingredientsItem.setRestaurant(restaurant);
        ingredientsItem.setCategory(ingredientsCategory);
//        ingredientsItem.setInStock();

        IngredientsItem item=ingredientsItemRepository.save(ingredientsItem);
        ingredientsCategory.getIngredients().add(item);
        return item;
    }

    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) {
        return ingredientsItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> optionalIngredientsItem=ingredientsItemRepository.findById(id);
        if(optionalIngredientsItem.isEmpty()){
            throw new Exception("ingredients not found");
        }
        IngredientsItem ingredientsItem=optionalIngredientsItem.get();
        ingredientsItem.setInStock(!ingredientsItem.isInStock());
        return ingredientsItemRepository.save(ingredientsItem);
    }
}
