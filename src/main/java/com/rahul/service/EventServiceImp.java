package com.rahul.service;

import com.rahul.model.Events;
import com.rahul.model.Restaurant;
import com.rahul.repository.EventRepository;
import com.rahul.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImp implements EventService{

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private RestaurantService restaurantService;


    @Override
    public Events createEvent(Events event, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        Events newEvent= new Events();
        newEvent.setRestaurant(restaurant);
        newEvent.setImage(event.getImage());
        newEvent.setStartedAt(event.getStartedAt());
        newEvent.setEndsAt(event.getEndsAt());
        newEvent.setLocation(event.getLocation());
        newEvent.setName(event.getName());
        return eventRepository.save(newEvent);
    }

    @Override
    public List<Events> findAllEvent() {
        return eventRepository.findAll();
    }

    @Override
    public List<Events> findRestaurantEvents(Long id) {
        return eventRepository.findEventsByRestaurantId(id);
    }

    @Override
    public void deleteEvent(Long id) throws Exception {
    Events events = findById(id);
    eventRepository.delete(events);
    }

    @Override
    public Events findById(Long id) throws Exception {
        Optional<Events> optionalEvents=eventRepository.findById(id);
        if(optionalEvents.isPresent()){
            return optionalEvents.get();
        }
        throw  new Exception("Event not Found with id "+id);
    }
}
