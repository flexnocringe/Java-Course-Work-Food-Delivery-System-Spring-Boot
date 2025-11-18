package org.example.javacourseworkbackend.repositories;

import org.example.javacourseworkbackend.model.FoodItem;
import org.example.javacourseworkbackend.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodItemRepository extends JpaRepository<FoodItem, Integer> {
    List<FoodItem> findByRestaurant(Restaurant restaurant);
}
