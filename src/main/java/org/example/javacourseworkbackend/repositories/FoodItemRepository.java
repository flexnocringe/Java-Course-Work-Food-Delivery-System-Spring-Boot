package org.example.javacourseworkbackend.repositories;

import org.example.javacourseworkbackend.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemRepository extends JpaRepository<FoodItem, Integer> {
}
