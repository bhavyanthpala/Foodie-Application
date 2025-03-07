package com.example.RestaurantService.controller;

import com.example.RestaurantService.domain.Cuisine;
import com.example.RestaurantService.domain.Restaurant;
import com.example.RestaurantService.domain.User;
import com.example.RestaurantService.exception.CusineNotFound;
import com.example.RestaurantService.exception.RestaurantNotFoundException;
import com.example.RestaurantService.exception.UserAlreadyExists;
import com.example.RestaurantService.exception.UserNotFoundException;
import com.example.RestaurantService.service.IUserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/api/v2")
public class UserController {

    private IUserService iUserService;

    @Autowired
    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws UserAlreadyExists {
        User registeredUser = iUserService.registerUser(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PutMapping("/user/updateUsernameAndEmail/{userId}")
    public ResponseEntity<User> updateUsernameAndEmail(@PathVariable String userId,
                                                       @RequestBody User updatedUser) throws UserNotFoundException {
        User result = iUserService.updateUser(userId, updatedUser);
        if (result == null) {
            return ResponseEntity.badRequest().body(null); // Return 400 if update fails
        }
        return ResponseEntity.ok(result); // Return 200 OK with updated user
    }

    @DeleteMapping("/user/deleterestaurant/{restaurantId}")
    public ResponseEntity<?> deleteRestaurantFromFavouriteRestaurantList(@PathVariable String restaurantId, HttpServletRequest request)
            throws UserNotFoundException, RestaurantNotFoundException
    {
        System.out.println("header" +request.getHeader("Authorization"));
        Claims claims = (Claims) request.getAttribute("claims");
        String userId = (String) claims.get("userId");
        System.out.println(userId);
        User user =  iUserService.deleteFavouriteRestaurant(userId,restaurantId);

        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @DeleteMapping("/user/deletecuisine/{cuisineId}")
    public ResponseEntity<?> deleteCuisineFromFavouriteCuisineList(@PathVariable Long cuisineId, HttpServletRequest request)
            throws UserNotFoundException, CusineNotFound
    {
        System.out.println("header" +request.getHeader("Authorization"));
        Claims claims = (Claims) request.getAttribute("claims");
        String userId = (String) claims.get("userId");
        System.out.println(userId);
        User user =  iUserService.deleteFavouriteCuisine(userId,cuisineId);

        return new ResponseEntity<>(user,HttpStatus.OK);
    }


    @PostMapping("/user/{userId}/favorite-cuisine")
    public ResponseEntity<User> addFavoriteCuisine(
            @PathVariable String userId,
            @RequestBody Cuisine cuisine
    ) throws UserNotFoundException {

        User updatedUser = iUserService.addFavoriteCuisine(userId, cuisine);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/user/favorite-restaurant")
    public ResponseEntity<?> addFavoriteRestaurant(@RequestBody Restaurant restaurant, HttpServletRequest request) throws UserNotFoundException {
        System.out.println("Restaurant in Controller : " + restaurant);
        System.out.println("header" + request.getHeader("Authorization"));

        Claims claims = (Claims) request.getAttribute("claims");
        String userId = (String) claims.get("userId");
        System.out.println(userId);

        // Check if the restaurant is already a favorite for the user
        User user = iUserService.getUserById(userId);
        System.out.println(user);
        boolean isAlreadyFavorited = user != null
                && restaurant != null
                && user.getFavoriteRestaurants() != null
                && user.getFavoriteRestaurants().stream()
                .anyMatch(favRestaurant ->
                                favRestaurant != null
                                        && Objects.equals(favRestaurant.getRestaurantId(), restaurant.getRestaurantId()));

        if (isAlreadyFavorited) {
            // Return a response indicating the restaurant is already a favorite
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Restaurant is already in your favorites");
        }

        System.out.println("display UserId"+userId);
        System.out.println("display restaurant"+restaurant);
        User updatedUser = iUserService.addFavoriteRestaurant(userId, restaurant);
        System.out.println(updatedUser);
        System.out.println("Valid Request");
        return ResponseEntity.ok(updatedUser);
    }


    @GetMapping("/user/display-all-fav-restaurant")
    public ResponseEntity displayAllFavRestaurant(HttpServletRequest request) throws UserNotFoundException {
        System.out.println("/user/display-all-fav-restaurant");
        System.out.println("header" +request.getHeader("Authorization"));
        Claims claims = (Claims) request.getAttribute("claims");
        String userId = (String) claims.get("userId");
        System.out.println("UserId : "+userId);
        List<Restaurant> favRest = iUserService.favRestaurants(userId);
        return new ResponseEntity(favRest,HttpStatus.OK);
    }



}
