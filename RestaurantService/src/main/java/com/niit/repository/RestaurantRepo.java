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

@Repository
public interface RestaurantRepo extends MongoRepository<Restaurant, String> {
    @Query("{'restaurantByLocation':?0}")
    public Restaurant findByLocation(String restaurantLocation);
}
