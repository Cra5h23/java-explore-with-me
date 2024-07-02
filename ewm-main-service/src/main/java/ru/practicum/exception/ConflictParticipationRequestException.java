package ru.practicum.exception;

/**
 * @author Nikolay Radzivon
 * @Date 26.06.2024
 */
public class ConflictParticipationRequestException extends RuntimeException {
    public ConflictParticipationRequestException(String message) {
        super(message);
    }
}
