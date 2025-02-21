package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.model.Restaurant;
import com.examly.springapp.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
        }

        @GetMapping("/{id}")
        public Restaurant getRestaurantById(@PathVariable Long id) {
            return restaurantService.getRestaurantById(id);
        }

        @PostMapping
        public Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
            return restaurantService.saveRestaurant(restaurant);
            }

            @PutMapping("/{id}")
            public Restaurant updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant) {
                return restaurantService.updateRestaurant(id, restaurant);
            }

            @DeleteMapping("/{id}")
            public void deleteRestaurant(@PathVariable Long id) {
                restaurantService.deleteRestaurant(id);
                }
            }