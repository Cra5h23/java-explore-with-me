package ru.practicum.dto.event;

/**
 * Список состояний жизненного цикла события.
 *
 * @author Nikolay Radzivon
 * @Date 20.06.2024
 */
public enum EventState {
    /**
     * Событие рассматривается.
     */
    PENDING,

    /**
     * Событие опубликовано.
     */
    PUBLISHED,

    /**
     * Событие отменено.
     */
    CANCELED
}
