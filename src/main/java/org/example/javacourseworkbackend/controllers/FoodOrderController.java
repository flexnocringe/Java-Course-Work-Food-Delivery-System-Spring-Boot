package org.example.javacourseworkbackend.controllers;

import com.google.gson.Gson;
import org.example.javacourseworkbackend.errorHandling.UserNotFound;
import org.example.javacourseworkbackend.model.FoodOrder;
import org.example.javacourseworkbackend.model.Restaurant;
import org.example.javacourseworkbackend.model.User;
import org.example.javacourseworkbackend.repositories.FoodOrderRepository;
import org.example.javacourseworkbackend.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@RestController
public class FoodOrderController {
    @Autowired
    private FoodOrderRepository foodOrderRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping(value = "/allOrders")
    public @ResponseBody Iterable<FoodOrder> getAllFoodOrders() {
        return foodOrderRepository.findAll();
    }

    @GetMapping(value = "/restaurantOrders")
    public @ResponseBody Iterable<FoodOrder> getRestaurantFoodOrders(@RequestBody String restaurantInfo) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(restaurantInfo, Properties.class);
        Restaurant restaurant = restaurantRepository.findById(Integer.valueOf(properties.getProperty("id")))
                .orElseThrow(() -> new UserNotFound(Integer.valueOf(properties.getProperty("id"))));
        return foodOrderRepository.findByRestaurant(restaurant);
    }
}
