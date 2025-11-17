package org.example.javacourseworkbackend.repositories;

import org.example.javacourseworkbackend.model.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Integer> {
}
