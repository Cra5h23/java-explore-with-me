package ru.practicum.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.dto.category.CategoryDtoResponse;

/**
 * Dto события.
 *
 * @author Nikolay Radzivon
 * @Date 19.06.2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventShortDto {
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
    private String eventDate;

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
}
