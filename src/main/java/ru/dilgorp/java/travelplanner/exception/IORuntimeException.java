package ru.dilgorp.java.travelplanner.exception;

public class IORuntimeException extends RuntimeException {
    public IORuntimeException(Exception e) {
        super(e);
    }
}
