package com.rahul.repository;

import com.rahul.model.IngredientsItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientsItemRepository extends JpaRepository<IngredientsItem, Long> {

    public List<IngredientsItem> findByRestaurantId(Long id);


}
