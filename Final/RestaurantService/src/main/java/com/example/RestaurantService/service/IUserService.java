package com.example.RestaurantService.service;

import com.example.RestaurantService.domain.Cuisine;
import com.example.RestaurantService.domain.Restaurant;
import com.example.RestaurantService.domain.User;
import com.example.RestaurantService.exception.CusineNotFound;
import com.example.RestaurantService.exception.RestaurantNotFoundException;
import com.example.RestaurantService.exception.UserAlreadyExists;
import com.example.RestaurantService.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    User registerUser(User user) throws UserAlreadyExists;
    User updateUser(String userId, User updatedUser) throws UserNotFoundException;

    User deleteFavouriteRestaurant(String userId,String restaurantId) throws UserNotFoundException, RestaurantNotFoundException;

    User addFavoriteCuisine(String userId, Cuisine cuisine) throws UserNotFoundException;

    User addFavoriteRestaurant(String userId, Restaurant restaurant) throws UserNotFoundException;

    User deleteFavouriteCuisine(String userId, Long cuisineId) throws UserNotFoundException, CusineNotFound;

    List<Restaurant> favRestaurants(String userId) throws UserNotFoundException;
    User getUserById(String userId) throws UserNotFoundException;

}
