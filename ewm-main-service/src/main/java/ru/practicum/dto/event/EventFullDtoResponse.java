package ru.practicum.dto.event;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.practicum.json.CustomLocalDateTimeSerializer;

import java.time.LocalDateTime;

/**
 * @author Nikolay Radzivon
 * @Date 20.06.2024
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDtoResponse extends EventShortDto {
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
