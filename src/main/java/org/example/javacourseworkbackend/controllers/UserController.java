package org.example.javacourseworkbackend.controllers;

import com.google.gson.Gson;
import org.example.javacourseworkbackend.errorHandling.UserNotFound;
import org.example.javacourseworkbackend.model.BasicUser;
import org.example.javacourseworkbackend.model.Driver;
import org.example.javacourseworkbackend.model.User;
import org.example.javacourseworkbackend.repositories.BasicUserRepository;
import org.example.javacourseworkbackend.repositories.DriverRepository;
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
    @Autowired
    private BasicUserRepository basicUserRepository;
    @Autowired
    private DriverRepository driverRepository;

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

    @GetMapping(value = "/getUserById/{id}")
    public @ResponseBody EntityModel<User> getUserById(@PathVariable Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound(id));

        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("allUsers"));
    }

    @PostMapping(value = "/validateUser")
    public @ResponseBody EntityModel<User>  validateUser(@RequestBody String userInfoToValidate) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(userInfoToValidate, Properties.class);
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        return EntityModel.of(userRepository.findByUsernameAndPassword(username, password));
    }

    @PostMapping(value = "/createNewBasicUser")
    public @ResponseBody EntityModel<BasicUser> createNewBasicUser(@RequestBody BasicUser user) {
        return EntityModel.of(basicUserRepository.save(user));
    }

    @PostMapping(value = "/createNewDriver")
    public @ResponseBody EntityModel<Driver> createNewDriver(@RequestBody Driver driver) {
        return EntityModel.of(driverRepository.save(driver));
    }
}
