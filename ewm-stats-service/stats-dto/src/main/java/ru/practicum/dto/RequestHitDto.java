package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto для сущности посещения.
 *
 * @author Nikolay Radzivon
 * @Date 17.06.2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestHitDto {
    /**
     * IP-адрес пользователя, осуществившего запрос.
     */
    private String ip;

    /**
     * URI для которого был осуществлен запрос.
     */
    private String uri;

    /**
     * Идентификатор сервиса для которого записывается информация.
     */
    private String app;

    /**
     * Дата и время, когда был совершен запрос к эндпоинту.
     */
    private String timestamp;
}
