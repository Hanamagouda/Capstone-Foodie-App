/*
 * Author : Mushib Khan
 * Date : 03-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.Proxy;

import com.niit.domain.Cuisine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-customer-service", url = "localhost:8081")
public interface RestaurantProxy {

    @PostMapping("/customer/user/cart/{emailId}")
    ResponseEntity<?> addToCart(@PathVariable String emailId, @RequestBody Cuisine cuisine);
}
