package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.dto.event.EventState;

import java.time.LocalDateTime;

/**
 * Dto для заявки участия в событии
 *
 * @author Nikolay Radzivon
 * @Date 22.06.2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {
    /**
     * Дата и время создания заявки.
     */
    private LocalDateTime created;

    /**
     * Идентификационный номер события.
     */
    private Long event;

    /**
     * Идентификационный номер заявки.
     */
    private Long id;

    /**
     * Идентификационный номер пользователя, отправившего заявку.
     */
    private Long requester;

    /**
     * Статус заявки.
     */
    private EventState status;
}
