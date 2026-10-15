package ru.crm.product_service.exception;

public class InsufficientQuantityException extends RuntimeException {

    public InsufficientQuantityException() {
        super();
    }

    public InsufficientQuantityException(String message) {
        super(message);
    }

}
