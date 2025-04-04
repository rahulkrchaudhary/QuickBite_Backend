package com.rahul.repository;

import com.rahul.model.IngredientsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientsCategoryRepository extends JpaRepository<IngredientsCategory, Long> {

    List<IngredientsCategory> findByRestaurantId(Long id);
}
