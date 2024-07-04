package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 22.06.2024
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestStatusUpdateRequest {
    /**
     * Список идентификационных номеров запросов.
     */
    private List<Long> requestIds;

    /**
     * Новый статус запроса на участие в событии.
     */
    private EventRequestStatus status;
}
