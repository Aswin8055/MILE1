package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.FoodItem;

public interface FoodItemService {
    List<FoodItem> getAllFoodItems();
    FoodItem getFoodItemById(Long id);
    FoodItem saveFoodItem(FoodItem foodItem);
    FoodItem updateFoodItem(Long id, FoodItem foodItem);
    void deleteFoodItem(Long id);
    List<FoodItem> getFoodItemsByRestaurantId(Long restaurantId);
    }