package com.rahul.service;

import com.rahul.dto.ResourceNotFoundException;
import com.rahul.dto.RestaurantDto;
import com.rahul.model.Address;
import com.rahul.model.Category;
import com.rahul.model.Restaurant;
import com.rahul.model.User;
import com.rahul.repository.AddressRepository;
import com.rahul.repository.CategoryRepository;
import com.rahul.repository.RestaurantRepository;
import com.rahul.repository.UserRepository;
import com.rahul.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImp implements RestaurantService{
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public Restaurant creatRestaurant(CreateRestaurantRequest req, User user) {
        Address address= addressRepository.save(req.getAddress());
        Restaurant restaurant=new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDate.from(LocalDateTime.now()));
        restaurant.setOwner(user);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception {
        Restaurant restaurant=findRestaurantById(restaurantId);
        if(restaurant.getCuisineType()!=null){
            restaurant.setCuisineType(updateRestaurant.getCuisineType());
        }
        if(restaurant.getDescription()!=null){
            restaurant.setDescription(updateRestaurant.getDescription());
        }
        if(restaurant.getName()!=null){
                restaurant.setName(updateRestaurant.getName());}

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant=findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
     }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }



    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt=restaurantRepository.findById(id);
        if(opt.isEmpty()){
            throw new Exception("restaurant not found with id "+id);
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
//        Optional<Restaurant> restaurant = restaurantRepository.findByOwnerId(userId);
//        if (restaurant.isEmpty()) {
////            throw new ResourceNotFoundException("Restaurant not found for owner ID: " + userId);
//            throw new Exception("restaurant not found.....");
//        }
//        return restaurant.get();
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        return restaurant;

    }
//    @Override
//    public Restaurant getRestaurantByUserId(Long userId) {
//        return restaurantRepository.findByOwnerId(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with owner ID: " + userId));
//    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant=findRestaurantById(restaurantId);
        RestaurantDto dto= new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurantId);

        boolean isFavorites=false;
        List<RestaurantDto> favorites=user.getFavorites();
        for(RestaurantDto favorite : favorites){
            if(favorite.getId().equals(restaurantId)){
                isFavorites=true;
                break;
            }
        }
        if(isFavorites){
            favorites.removeIf(favorite->favorite.getId().equals(restaurantId));
        }else {
            favorites.add(dto);
        }
//        if(user.getFavorites().contains(dto)){
//            user.getFavorites().remove(dto);
//        }else{
//            user.getFavorites().add(dto);
//        }
        userRepository.save(user);
        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
       Restaurant restaurant= findRestaurantById(id);
       restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }

    @Override
    public List<Restaurant> searchRestaurantBYName(String keyword) {
         return restaurantRepository.findByRestaurantName(keyword);
    }
}
