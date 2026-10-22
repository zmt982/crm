package ru.crm.common_lib.exception;

public class PartnerUnavailableException extends RuntimeException {

    public PartnerUnavailableException() {
        super();
    }

    public PartnerUnavailableException(String message) {
        super(message);
    }

    public PartnerUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

}
