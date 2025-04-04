package com.rahul.repository;

import com.rahul.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Events, Long> {

    public List<Events> findEventsByRestaurantId(Long id);
}
