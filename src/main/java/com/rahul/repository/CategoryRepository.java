package com.rahul.repository;

import com.rahul.model.Category;
import com.rahul.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    public List<Category> findByRestaurant(Restaurant restaurant);
    public List<Category> findByRestaurantId(Long id);
}
