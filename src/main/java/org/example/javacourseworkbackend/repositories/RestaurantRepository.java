package org.example.javacourseworkbackend.repositories;

import org.example.javacourseworkbackend.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    Restaurant findById(int id);

    Restaurant getRestaurantById(int id);
}
