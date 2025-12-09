package org.example.javacourseworkbackend.errorHandling;

public class UserNotFound extends RuntimeException {
    public UserNotFound(Integer id) {
        super("Could not find user"+id);
    }

    public UserNotFound() {
        super("Could not find user");
    }
}
