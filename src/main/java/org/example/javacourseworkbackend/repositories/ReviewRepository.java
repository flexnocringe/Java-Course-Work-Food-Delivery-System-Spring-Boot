package org.example.javacourseworkbackend.repositories;

import org.example.javacourseworkbackend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Message, Integer> {
    List<Message> findByChatId(Integer chatId);
}
