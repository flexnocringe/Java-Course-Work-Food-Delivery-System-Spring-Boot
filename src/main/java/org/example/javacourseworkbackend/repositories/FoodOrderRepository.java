package org.example.javacourseworkbackend.repositories;

import org.example.javacourseworkbackend.model.BasicUser;
import org.example.javacourseworkbackend.model.FoodOrder;
import org.example.javacourseworkbackend.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Integer> {
    List<FoodOrder> findByRestaurant(Restaurant restaurant);

    List<FoodOrder> findByBuyer(BasicUser basicUser);
}
