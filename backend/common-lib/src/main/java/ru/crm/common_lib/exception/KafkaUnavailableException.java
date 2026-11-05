package ru.crm.common_lib.exception;

public class KafkaUnavailableException extends RuntimeException {

    public KafkaUnavailableException() {
        super();
    }

    public KafkaUnavailableException(String message) {
        super(message);
    }

    public KafkaUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

}
