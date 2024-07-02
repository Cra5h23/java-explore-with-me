package ru.practicum.exception;

/**
 * @author Nikolay Radzivon
 * @Date 26.06.2024
 */
public class NotFoundEventException extends RuntimeException {
    public NotFoundEventException(String message) {
        super(message);
    }
}
