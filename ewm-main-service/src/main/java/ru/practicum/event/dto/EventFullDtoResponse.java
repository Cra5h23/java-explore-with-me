package ru.practicum.event.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.category.dto.CategoryDtoResponse;
import ru.practicum.user.dto.UserShortDto;
import ru.practicum.json.CustomLocalDateTimeSerializer;

import java.time.LocalDateTime;

/**
 * @author Nikolay Radzivon
 * @Date 20.06.2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDtoResponse {
    /**
     * Краткая информация о событии.
     */
    private String annotation;

    /**
     * Категория.
     */
    private CategoryDtoResponse category;

    /**
     * Количество одобренных заявок на участие в данном событии
     */
    private Long confirmedRequests;

    /**
     * Дата и время на которое назначено событие.
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime eventDate;

    /**
     * Идентификационный номер события.
     */
    private Long id;

    /**
     * Организатор события.
     */
    private UserShortDto initiator;

    /**
     * Флаг требуется ли оплачивать участие. (true - нужно, false - нет).
     */
    private boolean paid;

    /**
     * Заголовок события.
     */
    private String title;

    /**
     * Количество просмотров события.
     */
    private Long views;

    /**
     * Дата и время создания события
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime createdOn;

    /**
     * Полное описание события.
     */
    private String description;

    /**
     * Координаты локации.
     */
    private Location location;

    /**
     * Ограничение на количество участников. (Значение 0 - означает отсутствие ограничения)
     */
    private int participantLimit;

    /**
     * Дата и время публикации события
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime publishedOn;

    /**
     * Нужна ли пре-модерация заявок на участие.
     */
    private boolean requestModeration;

    /**
     * Список состояний жизненного цикла события.
     */
    private EventState state;
}
