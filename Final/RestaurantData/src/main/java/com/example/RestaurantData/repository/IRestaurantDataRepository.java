package com.example.RestaurantData.repository;

import com.example.RestaurantData.domain.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRestaurantDataRepository extends MongoRepository<Restaurant,String> {
}
