package org.example.javacourseworkbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String text;
    private LocalDateTime dateCreated;
    @ManyToOne
    private User messageOwner;
    @JsonIgnore
    @ManyToOne
    private Chat chat;

    public Message(String text, LocalDateTime dateCreated, User messageOwner, Chat chat) {
        this.text = text;
        this.dateCreated = dateCreated;
        this.messageOwner = messageOwner;
        this.chat = chat;
    }

    @Override
    public String toString() {
        return messageOwner + " says:\n" + text + "\n| " + dateCreated+" |";
    }
}
