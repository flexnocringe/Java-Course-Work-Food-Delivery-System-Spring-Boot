package org.example.javacourseworkbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Restaurant extends BasicUser{
    @JsonIgnore
    @OneToMany(mappedBy ="restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FoodOrder> orderList;
    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FoodItem> menu;
    private String workHours;


    public Restaurant(String username, String password, String name, String surname, String phoneNumber, String address, List<FoodItem> menu, String workHours) {
        super(username, password, name, surname, phoneNumber, address);
        this.menu = menu;
        this.workHours = workHours;
    }

    public Restaurant(String username, String password, String name, String surname, String phoneNumber, LocalDateTime dateCreated, String address, String workHours) {
        super(username, password, name, surname, phoneNumber, dateCreated, address);
        this.workHours = workHours;
    }

    @Override
    public String toString() {
        return this.username;
    }
}
