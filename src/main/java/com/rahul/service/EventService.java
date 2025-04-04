package com.rahul.service;

import com.rahul.model.Events;

import java.util.List;

public interface EventService {

    public Events createEvent(Events event, Long restaurantId) throws Exception;

    public List<Events> findAllEvent();

    public List<Events> findRestaurantEvents(Long id);

    public void deleteEvent(Long id) throws Exception;

    public Events findById(Long id) throws Exception;
}
