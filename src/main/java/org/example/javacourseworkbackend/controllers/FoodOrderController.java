package org.example.javacourseworkbackend.controllers;

import com.google.gson.Gson;
import org.example.javacourseworkbackend.errorHandling.UserNotFound;
import org.example.javacourseworkbackend.model.FoodOrder;
import org.example.javacourseworkbackend.model.Restaurant;
import org.example.javacourseworkbackend.model.User;
import org.example.javacourseworkbackend.repositories.BasicUserRepository;
import org.example.javacourseworkbackend.repositories.FoodOrderRepository;
import org.example.javacourseworkbackend.repositories.RestaurantRepository;
import org.example.javacourseworkbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

@RestController
public class FoodOrderController {
    @Autowired
    private FoodOrderRepository foodOrderRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private BasicUserRepository basicUserRepository;

    @GetMapping(value = "/allOrders")
    public @ResponseBody Iterable<FoodOrder> getAllFoodOrders() {
        return foodOrderRepository.findAll();
    }

    @GetMapping(value = "/getRestaurantOrders/{id}")
    public @ResponseBody Iterable<FoodOrder> getRestaurantFoodOrders(@PathVariable int id) {
        Restaurant restaurant = restaurantRepository.findById(id);
        return foodOrderRepository.findByRestaurant(restaurant);
    }

    @GetMapping(value = "/getBuyersOrders/{id}")
    public @ResponseBody Iterable<FoodOrder> getBuyersFoodOrders(@PathVariable int id) {
        return foodOrderRepository.findByBuyer(basicUserRepository.findById(id));
    }

    @PostMapping(value = "/createNewOrder")
    public @ResponseBody EntityModel<FoodOrder> createNewOrder(@RequestBody FoodOrder foodOrder) {
        return EntityModel.of(foodOrderRepository.save(foodOrder));
    }
}
