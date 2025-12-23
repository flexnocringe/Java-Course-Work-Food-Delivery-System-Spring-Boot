package org.example.javacourseworkbackend.controllers;

import org.example.javacourseworkbackend.errorHandling.UserNotFound;
import org.example.javacourseworkbackend.model.FoodItem;
import org.example.javacourseworkbackend.model.Restaurant;
import org.example.javacourseworkbackend.repositories.FoodItemRepository;
import org.example.javacourseworkbackend.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FoodItemController {
    @Autowired
    private FoodItemRepository foodItemRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping(value = "/allFoodItems")
    public @ResponseBody Iterable<FoodItem> getAllFoodItems() {
        return foodItemRepository.findAll();
    }

    @GetMapping(value = "/restaurantFoodItems/{id}")
    public @ResponseBody Iterable<FoodItem> getRestaurantFoodItems(@PathVariable int id) {
        Restaurant restaurant = restaurantRepository.findById(id);
        if(restaurant == null) throw new UserNotFound(id);
        return foodItemRepository.findByRestaurant(restaurant);
    }
}
