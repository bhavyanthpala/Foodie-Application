package com.example.RestaurantData.service;

import com.example.RestaurantData.domain.Restaurant;
import com.example.RestaurantData.exception.RestaurantNotFoundException;

import java.util.List;

public interface IRestaurantDataService {
    Restaurant registerRestaurant(Restaurant restaurant);

    List<Restaurant> getAllRestaurants();

    void deleteAllRestaurants();

    Restaurant getRestaurantById(String restaurantId) throws RestaurantNotFoundException;

}
