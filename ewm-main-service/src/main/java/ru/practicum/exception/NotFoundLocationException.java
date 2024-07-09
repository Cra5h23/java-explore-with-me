package ru.practicum.exception;

/**
 * @author Nikolay Radzivon
 * @Date 07.07.2024
 */
public class NotFoundLocationException extends RuntimeException {
    public NotFoundLocationException(String message) {
        super(message);
    }
}
