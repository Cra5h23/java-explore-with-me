package ru.practicum.event.dto;

/**
 * Список статусов подтверждения запроса к событию.
 *
 * @author Nikolay Radzivon
 * @Date 22.06.2024
 */
public enum EventRequestStatus {
    /**
     * Запрос рассматривается.
     */
    PENDING,

    /**
     * Запрос подтверждён.
     */
    CONFIRMED,

    /**
     * Запрос отклонён.
     */
    REJECTED,

    /**
     * Запрос отменён.
     */
    CANCELED
}
