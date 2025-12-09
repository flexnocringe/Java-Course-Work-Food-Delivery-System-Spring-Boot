package org.example.javacourseworkbackend.repositories;

import org.example.javacourseworkbackend.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
    Chat findByFoodOrderId(Integer foodOrderId);
}
