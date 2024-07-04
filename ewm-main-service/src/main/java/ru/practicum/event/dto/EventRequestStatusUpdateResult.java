package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.participation.dto.ParticipationRequestDto;

import java.util.List;

/**
 * Результат подтверждения/отклонения заявок на участие в событии
 *
 * @author Nikolay Radzivon
 * @Date 22.06.2024
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestStatusUpdateResult {
    /**
     * Список подтверждённых заявок.
     */
    List<ParticipationRequestDto> confirmedRequests;

    /**
     * Список отклонённых заявок.
     */
    List<ParticipationRequestDto> rejectedRequests;
}
