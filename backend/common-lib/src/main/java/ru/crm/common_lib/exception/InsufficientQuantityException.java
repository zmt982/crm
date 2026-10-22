package ru.crm.common_lib.exception;

public class InsufficientQuantityException extends RuntimeException {

    public InsufficientQuantityException() {
        super();
    }

    public InsufficientQuantityException(String message) {
        super(message);
    }

}
