package ru.practicum.exception;

/**
 * @author Nikolay Radzivon
 * @Date 10.07.2024
 */
public class ConflictLocationException extends RuntimeException {
    public ConflictLocationException(String message) {
        super(message);
    }
}
