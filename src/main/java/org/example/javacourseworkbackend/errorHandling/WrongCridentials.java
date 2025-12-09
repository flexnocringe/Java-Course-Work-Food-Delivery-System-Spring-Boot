package org.example.javacourseworkbackend.errorHandling;

public class WrongCridentials extends RuntimeException {
    public WrongCridentials() {
        super("Wrong username or password");
    }
}
