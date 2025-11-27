package org.example.javacourseworkbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String chatText;
    private LocalDate dateCreated;
    @JsonIgnore
    @OneToOne(mappedBy ="chat")
    private FoodOrder foodOrder;
    @JsonIgnore
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Review> messages = new ArrayList<>();

    public Chat(String name, LocalDate dateCreated, FoodOrder foodOrder) {
        this.name = name;
        this.dateCreated = dateCreated;
        this.foodOrder = foodOrder;
        this.messages = new ArrayList<>();
    }
}
