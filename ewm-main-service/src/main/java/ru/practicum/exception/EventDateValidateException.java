package ru.practicum.exception;

/**
 * @author Nikolay Radzivon
 * @Date 25.06.2024
 */
public class EventDateValidateException extends RuntimeException {
    public EventDateValidateException(String message) {
        super(message);
    }
}
