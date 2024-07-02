package ru.practicum.exception;

/**
 * @author Nikolay Radzivon
 * @Date 02.07.2024
 */
public class NotFoundCompilationException extends RuntimeException {
    public NotFoundCompilationException(String message) {
        super(message);
    }
}
