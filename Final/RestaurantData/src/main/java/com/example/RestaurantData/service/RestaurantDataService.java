package com.example.RestaurantData.service;

import com.example.RestaurantData.domain.Restaurant;
import com.example.RestaurantData.exception.RestaurantNotFoundException;
import com.example.RestaurantData.repository.IRestaurantDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantDataService implements IRestaurantDataService{

    private IRestaurantDataRepository iRestaurantDataRepository;

    @Autowired
    public RestaurantDataService(IRestaurantDataRepository iRestaurantDataRepository) {
        this.iRestaurantDataRepository = iRestaurantDataRepository;
    }

    @Override
    public Restaurant registerRestaurant(Restaurant restaurant) {
        return this.iRestaurantDataRepository.save(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return this.iRestaurantDataRepository.findAll();
    }

    @Override
    public void deleteAllRestaurants() {
        this.iRestaurantDataRepository.deleteAll();
    }

    @Override
    public Restaurant getRestaurantById(String restaurantId) throws RestaurantNotFoundException {
        Optional<Restaurant> restaurantOptional = this.iRestaurantDataRepository.findById(restaurantId);
        if(restaurantOptional.isEmpty()){
            throw new RestaurantNotFoundException();
        }

        return restaurantOptional.get();
    }


}
