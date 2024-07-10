package ru.practicum.exception;

/**
 * @author Nikolay Radzivon
 * @Date 10.07.2024
 */
public class BadRequestLocationException extends RuntimeException {
    public BadRequestLocationException(String message) {
        super(message);
    }
}
