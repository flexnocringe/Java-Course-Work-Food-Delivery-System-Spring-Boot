package org.example.javacourseworkbackend.repositories;

import org.example.javacourseworkbackend.model.BasicUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicUserRepository extends JpaRepository<BasicUser, Integer> {
    BasicUser findById(int id);
}
