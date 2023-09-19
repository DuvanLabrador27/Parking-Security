package com.duvanlabrador.parking.Exception;

public class UserWithSameUsernameAlreadyExists extends RuntimeException {
    public UserWithSameUsernameAlreadyExists(String message) {
        super(message);
    }
}