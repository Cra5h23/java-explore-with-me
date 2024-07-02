package ru.practicum.exception;

/**
 * @author Nikolay Radzivon
 * @Date 25.06.2024
 */
public class NotFoundCategoryException extends RuntimeException {
    public NotFoundCategoryException(String message) {
        super(message);
    }
}
