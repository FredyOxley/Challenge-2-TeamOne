package com.compassuol.sp.challenge.ecommerce.domain.produto.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
