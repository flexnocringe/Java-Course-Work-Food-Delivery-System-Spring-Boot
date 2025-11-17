package org.example.javacourseworkbackend.repositories;

import org.example.javacourseworkbackend.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
}
