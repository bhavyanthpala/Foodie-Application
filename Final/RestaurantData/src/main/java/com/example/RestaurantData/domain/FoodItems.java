package com.example.RestaurantData.domain;

public class FoodItems {

    private String dishName;
    private double price;
    private float rating;
    private String dishDescription;

    public FoodItems(String dishName, double price, float rating, String dishDescription) {
        this.dishName = dishName;
        this.price = price;
        this.rating = rating;
        this.dishDescription = dishDescription;
    }

    public FoodItems(){

    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDishDescription() {
        return dishDescription;
    }

    public void setDishDescription(String dishDescription) {
        this.dishDescription = dishDescription;
    }
}
