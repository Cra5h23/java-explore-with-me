package ru.practicum.dto.event;

/**
 * Список состояния ревью события.
 *
 * @author Nikolay Radzivon
 * @Date 20.06.2024
 */
public enum StateReview {
    /**
     * Отправить на ревью.
     */
    SEND_TO_REVIEW,

    /**
     * Отменить ревью.
     */
    CANCEL_REVIEW
}
