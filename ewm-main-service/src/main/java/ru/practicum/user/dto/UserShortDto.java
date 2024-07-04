package ru.practicum.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto пользователя.
 *
 * @author Nikolay Radzivon
 * @Date 19.06.2024
 */
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class UserShortDto {
    /**
     * Идентификационный номер пользователя.
     */
    private Long id;

    /**
     * Имя пользователя.
     */
    private String name;
}
