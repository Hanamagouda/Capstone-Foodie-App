/*
 * Author : Mushib Khan
 * Date : 03-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.controller;

import com.niit.domain.Cuisine;
import com.niit.domain.Order;
import com.niit.exception.CuisineNotFoundException;
import com.niit.exception.OrderAlreadyExistsException;
import com.niit.exception.OrderNotFoundException;
import com.niit.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/addOrder")
    public ResponseEntity<?> addOrder(@RequestBody Order order) {
        try {
            Order orderAdded = orderService.addOrder(order);
            if (orderAdded == null) {
                throw new OrderAlreadyExistsException();
            } else {
                return new ResponseEntity<Order>(orderAdded, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to add order", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable String orderId) {
        try {
            List<Order> orders = orderService.deleteOrder(orderId);
            if (orders == null) {
                throw new OrderNotFoundException();
            } else {
                return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>("Error Occurred while trying to delete order", HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/addCuisineToOrder/{orderId}")
    public ResponseEntity<?> addCuisineToOrder(@PathVariable String orderId, @RequestBody Cuisine cuisine) {
        try {
            List<Cuisine> cuisines = orderService.addCuisineToOrder(orderId, cuisine);
            if (cuisines == null) {
                throw new CuisineNotFoundException();
            } else {
                return new ResponseEntity<List<Cuisine>>(cuisines, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to add Cuisine", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getOrder")
    public ResponseEntity<?> getOrder() {
        try {
            List<Order> order = orderService.getOrder();
            if (order == null) {
                throw new OrderNotFoundException();
            } else {
                return new ResponseEntity<List<Order>>(order, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>("Error Occurred while trying to fetch order", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteFromOrder/{orderId}/{cuisineId}")
    public ResponseEntity<?> deleteOrderFrom(@PathVariable String orderId, @PathVariable int cuisineId) {
        try {
            List<Cuisine> cuisines = orderService.deleteFromOrder(orderId, cuisineId);
            if (cuisines == null) {
                throw new CuisineNotFoundException();
            } else {
                return new ResponseEntity<List<Cuisine>>(cuisines, HttpStatus.OK);
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>("Error Occurred while trying to delete Order", HttpStatus.NOT_FOUND);
        }
    }
}
