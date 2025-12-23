package org.example.javacourseworkbackend.controllers;

import com.google.gson.Gson;
import org.example.javacourseworkbackend.model.Chat;
import org.example.javacourseworkbackend.model.FoodOrder;
import org.example.javacourseworkbackend.model.Message;
import org.example.javacourseworkbackend.repositories.BasicUserRepository;
import org.example.javacourseworkbackend.repositories.ChatRepository;
import org.example.javacourseworkbackend.repositories.FoodOrderRepository;
import org.example.javacourseworkbackend.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Properties;

@RestController
public class ChatController {
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private FoodOrderRepository foodOrderRepository;
    @Autowired
    private BasicUserRepository basicUserRepository;

    @GetMapping(value = "getMessagesForOrder/{id}")
    public @ResponseBody Iterable<Message> getMessagesForOrder(@PathVariable int id) {
        Chat chat = chatRepository.findByFoodOrderId(id);
        if(chat == null){
            FoodOrder order = foodOrderRepository.findById(id);
            Chat newChat = new Chat("Chat for order: " + order.getName(), LocalDate.now(), order);
            order.setChat(newChat);
            chatRepository.save(newChat);
        }
        return chatRepository.findByFoodOrderId(id).getMessages();
    }

    @PostMapping(value = "sendMessage")
    public @ResponseBody String sendMessage(@RequestBody String info) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(info, Properties.class);
        String messageText = properties.getProperty("messageText");
        var messageOwner = basicUserRepository.getBasicUserById(Integer.valueOf(properties.getProperty("userId")));
        var order = foodOrderRepository.getFoodOrderById(Integer.valueOf(properties.getProperty("orderId")));

        Message message = new Message(messageText, LocalDateTime.now(), messageOwner, order.getChat());
        reviewRepository.save(message);

        return "success";

    }
}
