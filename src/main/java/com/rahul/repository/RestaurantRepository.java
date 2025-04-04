package com.rahul.repository;

import com.rahul.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r FROM Restaurant r WHERE lower(r.name) LIKE lower(concat('%',:query, '%'))"+
                    "OR lower(r.cuisineType) LIKE lower(concat('%', :query, '%'))")
    List<Restaurant> findBySearchQuery(String query);

    @Query("SELECT f FROM Restaurant f WHERE LOWER(f.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Restaurant> findByRestaurantName(@Param("keyword") String keyword);


    Restaurant findByOwnerId(Long userId);
//    Optional<Restaurant> findByOwnerId(Long userId);

}
