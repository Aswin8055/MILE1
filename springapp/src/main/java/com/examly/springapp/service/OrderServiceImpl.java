package com.examly.springapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Order;
import com.examly.springapp.model.User;
import com.examly.springapp.model.FoodItem;
import com.examly.springapp.repository.OrderRepository;
import com.examly.springapp.repository.UserRepository;
import com.examly.springapp.repository.FoodItemRepository;
import com.examly.springapp.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    @Override
    public Order saveOrder(Order order) {
        // Verify user exists
        User user = userRepository.findById(order.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " +
                        order.getUser().getId()));

        // Verify food item exists
        FoodItem foodItem = foodItemRepository.findById(order.getFoodItem().getId())
                .orElseThrow(() -> new RuntimeException("Food item not found with id: " +
                        order.getFoodItem().getId()));

        order.setUser(user);
        order.setFoodItem(foodItem);

        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        Order existingOrder = getOrderById(id);

        // If user is being updated, verify new user exists
        if (order.getUser() != null &&
                !order.getUser().getId().equals(existingOrder.getUser().getId())) {
            User newUser = userRepository.findById(order.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " +
                            order.getUser().getId()));
            existingOrder.setUser(newUser);
        }

        // If food item is being updated, verify new food item exists
        if (order.getFoodItem() != null &&
                !order.getFoodItem().getId().equals(existingOrder.getFoodItem().getId())) {
            FoodItem newFoodItem = foodItemRepository.findById(order.getFoodItem().getId())
                    .orElseThrow(() -> new RuntimeException("Food item not found with id: " +
                            order.getFoodItem().getId()));
            existingOrder.setFoodItem(newFoodItem);
        }

        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getOrdersByRestaurantId(Long restaurantId) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getFoodItem().getRestaurant().getId().equals(restaurantId))
                .collect(Collectors.toList());
    }
}