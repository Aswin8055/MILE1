package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.Order;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    Order saveOrder(Order order);
    Order updateOrder(Long id, Order order);
    void deleteOrder(Long id);
    List<Order> getOrdersByUserId(Long userId);
    List<Order> getOrdersByRestaurantId(Long restaurantId);
}