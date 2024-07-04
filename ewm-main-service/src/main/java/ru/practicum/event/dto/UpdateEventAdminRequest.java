package ru.practicum.event.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.json.CustomLocalDateTimeDeserializer;
import ru.practicum.validator.EventDate;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author Nikolay Radzivon
 * @Date 22.06.2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventAdminRequest {
    /**
     * Новое краткое описание события.
     */
    @Size(min = 20, max = 2000, message = "Краткое описание должно быть не меньше {min} и не больше {max} символов")
    private String annotation;

    /**
     * Новая категория события.
     */
    private Long category;

    /**
     * Новое полное описание события.
     */
    @Size(min = 20, max = 7000, message = "Полное описание должно быть не меньше {min} и не больше {max} символов")
    private String description;

    /**
     * Новая дата и время события.
     */
    @EventDate
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime eventDate;

    /**
     * Новые координаты локации.
     */
    private Location location;

    /**
     * Новое значение флага платности мероприятия.
     */
    private Boolean paid;

    /**
     * Новый лимит пользователей.
     */
    @PositiveOrZero
    private Integer participantLimit;

    /**
     * Новое значение флага нужна ли пре-модерация заявок на участие.
     */
    private Boolean requestModeration;

    /**
     * Изменение состояния события
     */
    private EventPublishedState stateAction;

    /**
     * Новый заголовок события.
     */
    @Size(min = 3, max = 120, message = "Новый заголовок не может быть меньше {min} и больше {max} символов")
    private String title;
}
