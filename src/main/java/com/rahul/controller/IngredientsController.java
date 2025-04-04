package com.rahul.controller;

import com.rahul.model.IngredientsCategory;
import com.rahul.model.IngredientsItem;
import com.rahul.request.IngredientCategoryRequest;
import com.rahul.request.IngredientRequest;
import com.rahul.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientsController {

    @Autowired
    private IngredientsService ingredientsService;

    @PostMapping("/category")
    public ResponseEntity<IngredientsCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest req) throws Exception {
        IngredientsCategory item=ingredientsService.createIngredientsCategory(req.getName(), req.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<IngredientsItem> createIngredientItem(@RequestBody IngredientRequest req) throws Exception {
        IngredientsItem ingredientsItem=ingredientsService.createIngredientsItem(req.getRestaurantId(), req.getName(), req.getCategoryId());
        return new ResponseEntity<>(ingredientsItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/instock")
    public ResponseEntity<IngredientsItem> updateIngredientStock(@PathVariable Long id) throws Exception {
        IngredientsItem ingredientsItem=ingredientsService.updateStock(id);
        return new ResponseEntity<>(ingredientsItem, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredient(@PathVariable Long id) throws Exception {
        List<IngredientsItem> ingredientsItem=ingredientsService.findRestaurantIngredients(id);
        return new ResponseEntity<>(ingredientsItem, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientsCategory>> getRestaurantIngredientCategory(@PathVariable Long id) throws Exception {
        List<IngredientsCategory> ingredientsCategories=ingredientsService.findIngredientsCategoryByRestaurantId(id);
        return new ResponseEntity<>(ingredientsCategories, HttpStatus.OK);
    }

}
