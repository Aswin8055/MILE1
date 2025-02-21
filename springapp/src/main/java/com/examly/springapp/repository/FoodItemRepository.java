package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.User;
import com.examly.springapp.model.Restaurant;
import com.examly.springapp.model.FoodItem;
import com.examly.springapp.model.Order;



@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
}

