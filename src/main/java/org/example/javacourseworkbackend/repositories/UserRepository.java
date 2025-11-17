package org.example.javacourseworkbackend.repositories;

import org.example.javacourseworkbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
