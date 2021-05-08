package com.example.shopping.basket.exception;

import java.util.UUID;

public class BasketDoesNotExistException extends RuntimeException {
    public BasketDoesNotExistException(UUID basketId) {
        super(String.format("The basket [%s] does not exist", basketId));
    }
}
