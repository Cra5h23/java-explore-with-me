package ru.practicum.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto для сущности пользователь. Используется в качестве ответа.
 *
 * @author Nikolay Radzivon
 * @Date 22.06.2024
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoResponse {
    /**
     * Электронная почта пользователя.
     */
    private String email;

    /**
     * Идентификационный номер пользователя.
     */
    private Long id;

    /**
     * Имя пользователя.
     */
    private String name;
}
