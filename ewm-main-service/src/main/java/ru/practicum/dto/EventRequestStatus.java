package ru.practicum.dto;

/**
 * Список статусов подтверждения запроса к событию.
 *
 * @author Nikolay Radzivon
 * @Date 22.06.2024
 */
public enum EventRequestStatus {
    /**
     * Запрос подтверждён.
     */
    CONFIRMED,

    /**
     * Запрос отклонён.
     */
    REJECTED
}
