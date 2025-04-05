package com.rahul.repository;

import com.rahul.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Events, Long> {

    public List<Events> findEventsByRestaurantId(Long id);
}
