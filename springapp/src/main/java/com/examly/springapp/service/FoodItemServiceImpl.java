package com.examly.springapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.FoodItem;
import com.examly.springapp.model.Restaurant;
import com.examly.springapp.repository.FoodItemRepository;
import com.examly.springapp.repository.RestaurantRepository;
import com.examly.springapp.service.FoodItemService;

@Service
public class FoodItemServiceImpl implements FoodItemService {

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodItemRepository.findAll();
    }

    @Override
    public FoodItem getFoodItemById(Long id) {
        return foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found with id: " + id));
    }

    @Override
    public FoodItem saveFoodItem(FoodItem foodItem) {
        // Verify restaurant exists
        Restaurant restaurant = restaurantRepository.findById(foodItem.getRestaurant().getId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " +
                        foodItem.getRestaurant().getId()));
        foodItem.setRestaurant(restaurant);
        return foodItemRepository.save(foodItem);
    }

    @Override
    public FoodItem updateFoodItem(Long id, FoodItem foodItem) {
        FoodItem existingFoodItem = getFoodItemById(id);

        // Update fields
        existingFoodItem.setName(foodItem.getName());
        existingFoodItem.setPrice(foodItem.getPrice());

        // If restaurant is being updated, verify it exists
        if (foodItem.getRestaurant() != null &&
                !foodItem.getRestaurant().getId().equals(existingFoodItem.getRestaurant().getId())) {
            Restaurant newRestaurant = restaurantRepository.findById(foodItem.getRestaurant().getId())
                    .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " +
                            foodItem.getRestaurant().getId()));
            existingFoodItem.setRestaurant(newRestaurant);
        }

        return foodItemRepository.save(existingFoodItem);
    }

    @Override
    public void deleteFoodItem(Long id) {
        foodItemRepository.deleteById(id);
    }

    @Override
    public List<FoodItem> getFoodItemsByRestaurantId(Long restaurantId) {
        return foodItemRepository.findAll().stream()
                .filter(item -> item.getRestaurant().getId().equals(restaurantId))
                .collect(Collectors.toList());
    }
}