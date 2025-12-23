package org.example.javacourseworkbackend.controllers;

import org.example.javacourseworkbackend.errorHandling.UserNotFound;
import org.example.javacourseworkbackend.model.Restaurant;
import org.example.javacourseworkbackend.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class RestaurantController {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping(value = "/allRestaurants")
    public @ResponseBody Iterable<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @GetMapping("/getRestaurantById/{id}")
    EntityModel<Restaurant> getRestaurantById(@PathVariable Integer id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new UserNotFound(id));
        return EntityModel.of(restaurant);
    }
}
