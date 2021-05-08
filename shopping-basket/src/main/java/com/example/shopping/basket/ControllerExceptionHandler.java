package com.example.shopping.basket;

import com.example.shopping.basket.exception.BasketDoesNotExistException;
import com.example.shopping.basket.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {BasketDoesNotExistException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage handleBasketDoesNotExistException(BasketDoesNotExistException ex) {
        return ErrorMessage.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
    }
}
