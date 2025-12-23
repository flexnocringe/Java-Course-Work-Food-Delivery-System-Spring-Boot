package org.example.javacourseworkbackend.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.example.javacourseworkbackend.errorHandling.FoodItemNotFound;
import org.example.javacourseworkbackend.model.*;
import org.example.javacourseworkbackend.repositories.BasicUserRepository;
import org.example.javacourseworkbackend.repositories.FoodItemRepository;
import org.example.javacourseworkbackend.repositories.FoodOrderRepository;
import org.example.javacourseworkbackend.repositories.RestaurantRepository;
import org.example.javacourseworkbackend.utils.LocalDateAdapter;
import org.example.javacourseworkbackend.utils.LocalDateTimeAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FoodOrderController {
    @Autowired
    private FoodOrderRepository foodOrderRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private BasicUserRepository basicUserRepository;
    @Autowired
    private FoodItemRepository foodItemRepository;

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
        return foodOrderRepository.findByBuyer(basicUserRepository.getBasicUserById(id));
    }

    @GetMapping(value = "/getReadyForPickupOrders")
    public @ResponseBody Iterable<FoodOrder> getReadyForPickupFoodOrders() {
        return foodOrderRepository.findFoodOrdersByOrderStatus(OrderStatus.READY_FOR_PICKUP.toString());
    }

    @GetMapping(value = "/getInDeliveryOrders")
    public @ResponseBody Iterable<FoodOrder> getInDeliveryFoodOrders() {
        return foodOrderRepository.findFoodOrdersByOrderStatus(OrderStatus.IN_DELIVERY.toString());
    }

    @PostMapping(value = "/createNewOrder")
    public @ResponseBody EntityModel<FoodOrder> createNewOrder(@RequestBody String foodOrderInfo) {
        GsonBuilder build = new GsonBuilder();
        build.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        build.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = build.setPrettyPrinting().create();
        JsonObject orderInfo = gson.fromJson(foodOrderInfo, JsonObject.class);
        int userId = orderInfo.get("userId").getAsInt();
        int restaurantId = orderInfo.get("restaurantId").getAsInt();
        Double price = orderInfo.get("price").getAsDouble();
        List<Integer> foodItem = gson.fromJson(orderInfo.getAsJsonArray("foodItem"), new TypeToken<List<Integer>>(){}.getType());
        List<FoodItem> foodItems = new ArrayList<>();
        for(Integer id : foodItem){
            foodItems.add(foodItemRepository.findById(id).orElseThrow(()-> new FoodItemNotFound()));
        }
        FoodOrder foodOrder = new FoodOrder("Order "+userId+restaurantId+" "+LocalDateTime.now().format(LocalDateTimeAdapter.formatter), price, foodItems, basicUserRepository.getBasicUserById(userId), null, restaurantRepository.getRestaurantById(restaurantId), OrderStatus.OPEN, LocalDateTime.now());
        return EntityModel.of(foodOrderRepository.save(foodOrder));
    }

    @PutMapping(value="pickupOrder/{id}")
    public @ResponseBody String pickupOrder(@PathVariable int id, @RequestBody Driver driver) {
        FoodOrder foodOrder = foodOrderRepository.findById(id);
        foodOrder.setOrderStatus(OrderStatus.IN_DELIVERY);
        foodOrder.setDriver(driver);
        foodOrderRepository.save(foodOrder);
        return "success";
    }

    @PutMapping(value="completeOrder/{id}")
    public @ResponseBody String completeOrder(@PathVariable int id) {
        FoodOrder foodOrder = foodOrderRepository.findById(id);
        foodOrder.setOrderStatus(OrderStatus.COMPLETED);
        foodOrderRepository.save(foodOrder);
        return "success";
    }
}
