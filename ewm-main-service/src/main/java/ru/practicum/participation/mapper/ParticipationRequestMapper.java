package ru.practicum.participation.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.dto.EventRequestStatus;
import ru.practicum.dto.ParticipationRequestDto;
import ru.practicum.event.model.Event;
import ru.practicum.participation.model.ParticipationRequest;
import ru.practicum.user.model.User;

import java.time.ZonedDateTime;

/**
 * @author Nikolay Radzivon
 * @Date 26.06.2024
 */
@Component
public class ParticipationRequestMapper {

    public ParticipationRequest toParticipationRequest(Long userId, Long eventId, EventRequestStatus status) {
        return ParticipationRequest.builder()
                .requester(User.builder()
                        .id(userId)
                        .build())
                .event(Event.builder()
                        .id(eventId)
                        .build())
                .status(status)
                .created(ZonedDateTime.now())
                .build();
    }

    public ParticipationRequestDto toParticipationRequestDto(ParticipationRequest save) {
        return ParticipationRequestDto.builder()
                .id(save.getId())
                .requester(save.getRequester().getId())
                .created(save.getCreated().toLocalDateTime())
                .event(save.getEvent().getId())
                .status(save.getStatus())
                .build();
    }
}
