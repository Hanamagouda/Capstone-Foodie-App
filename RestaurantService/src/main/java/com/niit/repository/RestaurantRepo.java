/*
 * Author : Mushib Khan
 * Date : 03-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.repository;

import com.niit.domain.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepo extends MongoRepository<Restaurant, Integer> {
    @Query("{'restaurantLocation' : {$in : [?0]}}")
    public List<Restaurant> findByLocation(String restaurantLocation);
}