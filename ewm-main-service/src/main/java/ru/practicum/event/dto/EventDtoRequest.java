package ru.practicum.event.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.json.CustomLocalDateTimeDeserializer;
import ru.practicum.json.CustomLocalDateTimeSerializer;
import ru.practicum.validator.EventDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Dto событие. Используется для получения данных из запроса.
 *
 * @author Nikolay Radzivon
 * @Date 20.06.2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDtoRequest {
    /**
     * Краткое описание события.
     */
    @NotBlank
    @Size(min = 20, max = 2000, message = "Краткое описание события не может быть меньше {min} и больше {max} символов")
    private String annotation;

    /**
     * Идентификационный номер категории к которой относится событие.
     */
    @NotNull(message = "Идентификационный номер категории не может быть пустым")
    private Long category;

    /**
     * Полное описание события.
     */
    @NotBlank
    @Size(min = 20, max = 7000, message = "Полное описание события не может быть меньше {min} и больше {max} символов")
    private String description;

    /**
     * Дата и время на которые намечено событие.
     */
    @NotNull(message = "Время начала события не должно быть null")
    @EventDate
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime eventDate;

    /**
     * Координаты локации.
     */
    @NotNull
    private Location location;

    /**
     * Флаг требования оплаты.
     */
    @Builder.Default
    private boolean paid = false;

    /**
     * Ограничение на количество участников. (0 - отсутствие ограничений)
     */
    @Builder.Default
    @PositiveOrZero
    private int participantLimit = 0;

    /**
     * Флаг нужна ли пре-модерация заявок на участие. Если true, то все заявки будут ожидать подтверждения инициатором события. Если false - то будут подтверждаться автоматически.
     */
    @Builder.Default
    private boolean requestModeration = true;

    /**
     * Заголовок события.
     */
    @NotBlank
    @Size(min = 3, max = 120, message = "Заголовок события не должен быть меньше {min} и больше {max} символов")
    private String title;
}
