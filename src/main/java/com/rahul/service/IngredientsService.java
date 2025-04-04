package com.rahul.service;

import com.rahul.model.IngredientsCategory;
import com.rahul.model.IngredientsItem;

import java.util.List;

//ingredients category and ingredients item
public interface IngredientsService {

    public IngredientsCategory createIngredientsCategory(String name, Long restaurantId) throws Exception;

    public IngredientsCategory findIngredientsCategoryById(Long id) throws Exception;

    public List<IngredientsCategory> findIngredientsCategoryByRestaurantId(Long id) throws Exception;

    public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception;

    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId);

    public IngredientsItem updateStock(Long id) throws Exception;
}
