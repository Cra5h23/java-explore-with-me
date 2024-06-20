package ru.practicum.dto.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.practicum.json.CustomLocalDateTimeDeserializer;
import ru.practicum.validator.EventDate;

import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Dto для класса сущности событие. Используется при обновлении события.
 *
 * @author Nikolay Radzivon
 * @Date 20.06.2024
 */
public class UpdateEventUserRequest {
    /**
     * Новое краткое описание события.
     */
    @Null
    @Size(min = 20, max = 2000, message = "Краткое описание должно быть не меньше {min} и не больше {max} символов")
    private String annotation;

    /**
     * Новая категория события.
     */
    @Null
    private Long category;

    /**
     * Новое полное описание события.
     */
    @Null
    @Size(min = 20, max = 7000, message = "Полное описание должно быть не меньше {min} и не больше {max} символов")
    private String description;

    /**
     * Новая дата и время события.
     */
    @Null
    @EventDate
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime eventDate;

    /**
     * Новые координаты локации.
     */
    @Null
    private Location location;

    /**
     * Новое значение флага платности мероприятия.
     */
    @Null
    private Boolean paid;

    /**
     * Новый лимит пользователей.
     */
    @Null
    private Integer participantLimit;

    /**
     * Новое значение флага нужна ли пре-модерация заявок на участие.
     */
    @Null
    private Boolean requestModeration;

    /**
     * Изменение состояния события
     */
    @Null
    private StateReview stateAction;

    /**
     * Новый заголовок события.
     */
    @Null
    private String title;

    //todo поиграть с группами валидации для полей которые могут быть нулл и должны валидироваться
}
