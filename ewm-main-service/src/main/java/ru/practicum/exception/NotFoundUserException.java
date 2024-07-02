package ru.practicum.exception;

/**
 * @author Nikolay Radzivon
 * @Date 23.06.2024
 */
public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException(String message) {
        super(message);
    }
}
