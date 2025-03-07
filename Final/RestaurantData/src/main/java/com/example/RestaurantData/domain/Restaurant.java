package com.example.RestaurantData.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Restaurant {

    @Id
    private String restaurantId;
    private String name;
    private String location;
    private String imagePath;
    private List<Cuisine> cuisine;  // Corrected to List<Cuisine>
    private List<FoodItems> foodItems;

    public Restaurant() {}

    public Restaurant(String restaurantId, String name, String location, String imagePath,List<Cuisine> cuisine, List<FoodItems> foodItems) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.location = location;
        this.imagePath = imagePath;
        this.cuisine = cuisine;
        this.foodItems = foodItems;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Cuisine> getCuisine() {
        return cuisine;  // Return list of Cuisine objects
    }

    public void setCuisine(List<Cuisine> cuisine) {
        this.cuisine = cuisine;  // Set list of Cuisine objects
    }

    public List<FoodItems> getFoodItems() {
        return foodItems;
    }
    public void setFoodItems(List<FoodItems> foodItems) {
        this.foodItems = foodItems;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
