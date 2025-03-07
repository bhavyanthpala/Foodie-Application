package com.example.RestaurantData.controller;

import com.example.RestaurantData.domain.Restaurant;
import com.example.RestaurantData.exception.RestaurantNotFoundException;
import com.example.RestaurantData.service.IRestaurantDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/api/v3")
public class RestaurantDataController {

    private IRestaurantDataService iRestaurantDataService;

    @Autowired
    public RestaurantDataController(IRestaurantDataService iRestaurantDataService) {
        this.iRestaurantDataService = iRestaurantDataService;
    }

    @PostMapping("/register")
    public ResponseEntity<Restaurant> registerUser(@RequestBody Restaurant restaurant){
        Restaurant restaurant1 = iRestaurantDataService.registerRestaurant(restaurant);
        return new ResponseEntity<>(restaurant1, HttpStatus.CREATED);
    }

    @GetMapping("/get-all-restaurants")
    public ResponseEntity displayAllFavRestaurant() {
        List<Restaurant> allRest = iRestaurantDataService.getAllRestaurants();
        return new ResponseEntity(allRest,HttpStatus.OK);
    }

    @DeleteMapping("/delete-all-restaurants")
    public ResponseEntity<Void> deleteAllRestaurant(){
        iRestaurantDataService.deleteAllRestaurants();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-restaurant-by-id/{restaurantId}")
    public ResponseEntity displayAllFavRestaurant(@PathVariable String restaurantId) throws RestaurantNotFoundException {
        Restaurant RestById = iRestaurantDataService.getRestaurantById(restaurantId);
        System.out.println(restaurantId);
        return new ResponseEntity(RestById,HttpStatus.OK);
    }

}
