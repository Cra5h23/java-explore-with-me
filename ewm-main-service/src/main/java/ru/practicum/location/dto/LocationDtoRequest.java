package ru.practicum.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.Marker;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Nikolay Radzivon
 * @Date 07.07.2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDtoRequest {
    /**
     *
     */
    @NotNull(groups = Marker.OnCreate.class)
    private Float lon;

    @NotNull(groups = Marker.OnCreate.class)
    private Float lat;

    @NotNull(groups = Marker.OnCreate.class)
    @Size(min = 1, max = 30, message = "Радиус локации не может быть меньше {min} и больше {max} км", groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    private Float radius;

    @NotBlank(message = "Название локации не может быть пустым", groups = Marker.OnCreate.class)
    @Size(min = 3, max = 50, message = "Название локации не может быть меньше {min} и больше {max} символов", groups = {Marker.OnCreate.class, Marker.OnUpdate.class} )
    private String name;

    @NotBlank(groups = Marker.OnCreate.class)
    @Size(min = 20, max = 2000, message = "Описание локации не может быть меньше {min} и больше {max} символов",groups = {Marker.OnCreate.class, Marker.OnUpdate.class})
    private String description;
}
