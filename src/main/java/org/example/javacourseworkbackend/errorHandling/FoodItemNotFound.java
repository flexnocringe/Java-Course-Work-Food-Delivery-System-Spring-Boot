package org.example.javacourseworkbackend.errorHandling;

public class FoodItemNotFound extends RuntimeException {
    public FoodItemNotFound() {
        super("Could Not Find Food Item");
    }
}
