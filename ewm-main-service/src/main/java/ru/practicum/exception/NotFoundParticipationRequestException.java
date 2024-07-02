package ru.practicum.exception;

/**
 * @author Nikolay Radzivon
 * @Date 27.06.2024
 */
public class NotFoundParticipationRequestException extends RuntimeException {
    public NotFoundParticipationRequestException(String message) {
        super(message);
    }
}
