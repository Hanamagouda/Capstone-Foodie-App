package com.niit.Proxy;

import com.niit.domain.Cuisine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-order-service", path = "localhost:8085")
public interface OrderProxy {
    @PostMapping("/addCuisineToOrder/{orderId}")
    public ResponseEntity<?> saveCuisineToOrder(@PathVariable String orderId, @RequestBody Cuisine cuisine);
}
