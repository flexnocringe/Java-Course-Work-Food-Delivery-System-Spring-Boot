package org.example.javacourseworkbackend.controllers;

import com.google.gson.Gson;
import org.example.javacourseworkbackend.errorHandling.UserNotFound;
import org.example.javacourseworkbackend.errorHandling.WrongCridentials;
import org.example.javacourseworkbackend.model.BasicUser;
import org.example.javacourseworkbackend.model.Driver;
import org.example.javacourseworkbackend.model.Salt;
import org.example.javacourseworkbackend.model.User;
import org.example.javacourseworkbackend.repositories.BasicUserRepository;
import org.example.javacourseworkbackend.repositories.DriverRepository;
import org.example.javacourseworkbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
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

    private final TextEncryptor passwordEncryptor = Encryptors.text("whatdoyoumean", Salt.getSalt());

    @GetMapping(value = "/allUsers")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PutMapping(value = "/updateBasicUser/{id}")
    public @ResponseBody String updateBasicUser(@RequestBody BasicUser updateUser, @PathVariable int id) {
        BasicUser user = basicUserRepository.findById(id)
                .orElseThrow(() -> new UserNotFound(id));
        user.setUsername(updateUser.getUsername());
        user.setPassword(passwordEncryptor.encrypt(updateUser.getPassword()));
        user.setName(updateUser.getName());
        user.setSurname(updateUser.getSurname());
        user.setPhoneNumber(updateUser.getPhoneNumber());
        user.setAddress(updateUser.getAddress());
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
        User user = userRepository.findByUsername(username);
        if(user==null){
            throw new UserNotFound();
        }
        if(!passwordEncryptor.decrypt(user.getPassword()).equals(password)) {
            throw new WrongCridentials();
        }
        user.setPassword(passwordEncryptor.decrypt(user.getPassword()));

        if(user instanceof Driver){
            user.setType("Driver");
        }else if(user instanceof BasicUser){
            user.setType("BasicUser");
        }else{
            user.setType("User");
        }
        return EntityModel.of(user);
    }

    @PostMapping(value = "/createNewBasicUser")
    public @ResponseBody EntityModel<BasicUser> createNewBasicUser(@RequestBody BasicUser user) {
        user.setPassword(passwordEncryptor.encrypt(user.getPassword()));
        return EntityModel.of(basicUserRepository.save(user));
    }

    @PostMapping(value = "/createNewDriver")
    public @ResponseBody EntityModel<Driver> createNewDriver(@RequestBody Driver driver) {
        driver.setPassword(passwordEncryptor.encrypt(driver.getPassword()));
        return EntityModel.of(driverRepository.save(driver));
    }

    @PutMapping(value = "/deleteUser")
    public @ResponseBody String deleteUser(@RequestBody User user) {
        userRepository.deleteById(user.getId());
        return "Success";
    }
}
