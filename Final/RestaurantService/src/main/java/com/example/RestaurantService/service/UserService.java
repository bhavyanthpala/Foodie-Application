package com.example.RestaurantService.service;

import com.example.RestaurantService.domain.Cuisine;
import com.example.RestaurantService.domain.Restaurant;
import com.example.RestaurantService.domain.User;
import com.example.RestaurantService.exception.CusineNotFound;
import com.example.RestaurantService.exception.RestaurantNotFoundException;
import com.example.RestaurantService.exception.UserAlreadyExists;
import com.example.RestaurantService.exception.UserNotFoundException;
import com.example.RestaurantService.proxy.UserProxy;
import com.example.RestaurantService.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{


    private IUserRepository iUserRepository;
    private UserProxy userProxy;

    @Autowired
    public UserService(IUserRepository iUserRepository,UserProxy userProxy) {
        this.iUserRepository = iUserRepository;
        this.userProxy=userProxy;
    }

//    @Override
//    public User registerUser(User user) throws UserAlreadyExists{
//        Optional<User> user1 = iUserRepository.findById(user.getUserId());
//        User user3 = iUserRepository.findByUsername(user.getUsername());
//        User user4 = iUserRepository.findByEmail(user.getEmail());
//
//        if(user1.isPresent() || user3==null || user4==null){throw new UserAlreadyExists();
//        }
//        User user2 = iUserRepository.save(user);
//        ResponseEntity responseEntity = userProxy.saveUser(user2);
//        System.out.println(responseEntity.getBody());
//        return user2;
//    }

    @Override
    public User registerUser(User user) throws UserAlreadyExists {
        Optional<User> user1 = iUserRepository.findById(user.getUserId());
        User user3 = iUserRepository.findByUsername(user.getUsername());
        User user4 = iUserRepository.findByEmail(user.getEmail());
        if (user1.isPresent() || user3 != null || user4 != null) {
            throw new UserAlreadyExists();
        }
        User savedUser = iUserRepository.save(user);
        ResponseEntity<?> responseEntity = userProxy.saveUser(savedUser);
        System.out.println(responseEntity.getBody());
        return savedUser;
    }


    @Override
    public User updateUser(String userId, User updatedUser) throws UserNotFoundException {
        // Retrieve the user by userId
        Optional<User> optionalUser = iUserRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = optionalUser.get();

        // Update the username and email if they are provided
        if (updatedUser.getUsername() != null && !updatedUser.getUsername().equals(user.getUsername())) {
            if (iUserRepository.findByUsername(updatedUser.getUsername()) != null) {
                return null; // Return null if the username is already taken
            }
            user.setUsername(updatedUser.getUsername());
        }

        if (updatedUser.getEmail() != null && !updatedUser.getEmail().equals(user.getEmail())) {
            if (iUserRepository.findByEmail(updatedUser.getEmail()) != null) {
                return null; // Return null if the email is already in use
            }
            user.setEmail(updatedUser.getEmail());
        }

        return iUserRepository.save(user);
    }

    @Override
    public User deleteFavouriteRestaurant(String userId, String restaurantId) throws UserNotFoundException, RestaurantNotFoundException {
        boolean restaurantIsPresent = false;
        Optional<User> optionalUser = iUserRepository.findById(userId);
        if(optionalUser.isEmpty()){ throw new UserNotFoundException();}
        User user = optionalUser.get();
        List<Restaurant> favRestaurantList = user.getFavoriteRestaurants();
        Iterator<Restaurant> iterator = favRestaurantList.iterator();
        while(iterator.hasNext()){
            Restaurant restaurant = iterator.next();
            if(restaurant.getRestaurantId().equals(restaurantId)){
                restaurantIsPresent = true;
                iterator.remove();
            }
        }
        if(restaurantIsPresent == false) {
            throw new RestaurantNotFoundException();
        }
        user.setFavoriteRestaurants(favRestaurantList);
        return iUserRepository.save(user);
    }

    public User deleteFavouriteCuisine(String userId, Long cuisineId) throws UserNotFoundException, CusineNotFound {

       boolean cusineIsPresent = false;
        Optional<User> optionalUser = iUserRepository.findById(userId);
        if(optionalUser.isEmpty()){ throw new UserNotFoundException();}
        User user = optionalUser.get();
        System.out.println(user);
        List<Cuisine> favCuisineList = user.getFavoriteCuisines();
        System.out.println(favCuisineList);
        Iterator<Cuisine> iterator = favCuisineList.iterator();
        while(iterator.hasNext()){
            Cuisine cuisine = iterator.next();
            if(cuisine.getCuisineId().equals(cuisineId)){
                cusineIsPresent = true;
                iterator.remove();
            }
        }

        if(cusineIsPresent == false) {
            throw new CusineNotFound();
        }
        user.setFavoriteCuisines(favCuisineList);
        return iUserRepository.save(user);
    }

    @Override
    public List<Restaurant> favRestaurants(String userId) throws UserNotFoundException {
        Optional<User> optionalUser = iUserRepository.findById(userId);
        System.out.println("Request is coming inside service");
        if(optionalUser.isEmpty()){throw new UserNotFoundException();}
        User user = optionalUser.get();

        List<Restaurant> favoriteRestaurant = user.getFavoriteRestaurants();
        return favoriteRestaurant;
    }


    @Override
    public User addFavoriteCuisine(String userId, Cuisine cuisine) throws UserNotFoundException{
        Optional<User> optionalUser = iUserRepository.findById(userId);

        if(optionalUser.isEmpty()){throw new UserNotFoundException();}
        User user = optionalUser.get();
        List<Cuisine> favoriteCuisine = user.getFavoriteCuisines();

        if (favoriteCuisine == null) {
            favoriteCuisine = new ArrayList<>();
        }
        if (!favoriteCuisine.contains(cuisine.getCuisineId())) {
            favoriteCuisine.add(cuisine);
            user.setFavoriteCuisines(favoriteCuisine);
        }
        return iUserRepository.save(user);
    }


    @Override
    public User addFavoriteRestaurant(String userId, Restaurant restaurant) throws UserNotFoundException {

        Optional<User> optionalUser = iUserRepository.findById(userId);
        System.out.println("Inside AddingFavoriteRestaurant Service");
        System.out.println("Restaurant Data: " + restaurant);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = optionalUser.get();


        List<Restaurant> favoriteRestaurants = user.getFavoriteRestaurants();


        if (favoriteRestaurants == null) {
            favoriteRestaurants = new ArrayList<>();
        }


        if (!favoriteRestaurants.contains(restaurant)) {
            favoriteRestaurants.add(restaurant);
            user.setFavoriteRestaurants(favoriteRestaurants);
        }



        return iUserRepository.save(user);

    }

    @Override
    public User getUserById(String userId) throws UserNotFoundException {
        Optional<User> userOptional = iUserRepository.findById(userId);

        if (userOptional.isPresent()) {
            System.out.println("User Is Present ");
            return userOptional.get();

        } else {
            System.out.println("User Is Not Present");
            throw new UserNotFoundException();
        }
    }

}
