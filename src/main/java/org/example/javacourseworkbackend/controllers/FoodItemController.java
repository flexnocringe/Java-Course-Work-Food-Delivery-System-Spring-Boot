package org.example.javacourseworkbackend.controllers;

import com.google.gson.Gson;
import org.example.javacourseworkbackend.errorHandling.UserNotFound;
import org.example.javacourseworkbackend.model.FoodItem;
import org.example.javacourseworkbackend.model.FoodOrder;
import org.example.javacourseworkbackend.model.Restaurant;
import org.example.javacourseworkbackend.repositories.FoodItemRepository;
import org.example.javacourseworkbackend.repositories.FoodOrderRepository;
import org.example.javacourseworkbackend.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

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

    @GetMapping(value = "/restaurantFoodItems")
    public @ResponseBody Iterable<FoodItem> getRestaurantFoodItems(@RequestBody String restaurantInfo) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(restaurantInfo, Properties.class);
        Restaurant restaurant = restaurantRepository.findById(Integer.valueOf(properties.getProperty("id")))
                .orElseThrow(() -> new UserNotFound(Integer.valueOf(properties.getProperty("id"))));
        return foodItemRepository.findByRestaurant(restaurant);
    }
}
