package org.example.javacourseworkbackend.controllers;

import com.google.gson.Gson;
import org.example.javacourseworkbackend.errorHandling.UserNotFound;
import org.example.javacourseworkbackend.model.User;
import org.example.javacourseworkbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/allUsers")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PutMapping(value = "/updateUser/{id}")
    public @ResponseBody String updateUser(@RequestBody String userInfoToUpdate, @PathVariable int id) {
        Gson gson = new Gson();//Helps me parse things from Json quickly
        Properties properties = gson.fromJson(userInfoToUpdate, Properties.class);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFound(id));
        user.setName(properties.getProperty("name"));
        user.setSurname(properties.getProperty("surname"));

        userRepository.save(user);
        return "Success";
    }

    @GetMapping("/getUserById/{id}")
    EntityModel<User> getUserById(@PathVariable Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound(id));

        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("allUsers"));
    }
}
