package org.example.javacourseworkbackend.repositories;

import org.example.javacourseworkbackend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
