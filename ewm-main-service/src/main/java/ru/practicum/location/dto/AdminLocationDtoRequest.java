package ru.practicum.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ActionType;

import javax.validation.constraints.*;

/**
 * @author Nikolay Radzivon
 * @Date 07.07.2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminLocationDtoRequest {
    /**
     *
     */
    @NotNull(groups = ActionType.OnCreate.class, message = "Поле lon не должно быть null")
    private Float lon;

    @NotNull(groups = ActionType.OnCreate.class, message = "Поле lat не должно быть null")
    private Float lat;

    @NotNull(groups = ActionType.OnCreate.class, message = "Поле radius не должно быть null")
    @Positive
    private Float radius;

    @NotBlank(message = "Название локации не может быть пустым", groups = ActionType.OnCreate.class)
    @Pattern(regexp = "^(?!\\s*$).+", message = "Название локации не может содержать только пробелы", groups = {ActionType.OnUpdate.class})
    @Size(min = 3, max = 50, message = "Название локации не может быть меньше {min} и больше {max} символов", groups = {ActionType.OnCreate.class, ActionType.OnUpdate.class})
    private String name;

    @NotBlank(groups = ActionType.OnCreate.class)
    @Pattern(regexp = "^(?!\\s*$).+", message = "Описание локации не может содержать только пробелы", groups = {ActionType.OnUpdate.class})
    @Size(min = 20, max = 2000, message = "Описание локации не может быть меньше {min} и больше {max} символов", groups = {ActionType.OnCreate.class, ActionType.OnUpdate.class})
    private String description;
}
