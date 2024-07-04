package ru.practicum.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Dto сущности пользователь. Используется для получения данных пользователя из запроса.
 *
 * @author Nikolay Radzivon
 * @Date 22.06.2024
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewUserRequest {
    /**
     * Адрес электронной почты пользователя.
     */
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Email
    @Size(min = 6, max = 254, message = "Адрес электронной почты не может быть меньше {min} и больше {max} символов")
    private String email;

    /**
     * Имя пользователя.
     */
    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(min = 2, max = 250, message = "Имя пользователя не может быть меньше {min} и больше {max} символов")
    private String name;
}
