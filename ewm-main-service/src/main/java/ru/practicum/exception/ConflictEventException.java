package ru.practicum.exception;

/**
 * @author Nikolay Radzivon
 * @Date 02.07.2024
 */
public class ConflictEventException extends RuntimeException {
    public ConflictEventException(String message) {
        super(message);
    }
}
