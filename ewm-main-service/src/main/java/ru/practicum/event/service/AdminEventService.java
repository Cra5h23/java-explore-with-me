package ru.practicum.event.service;

import lombok.Builder;
import lombok.Data;
import ru.practicum.dto.event.EventFullDtoResponse;
import ru.practicum.dto.event.EventState;
import ru.practicum.dto.event.UpdateEventAdminRequest;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 22.06.2024
 */
public interface AdminEventService {
    List<EventFullDtoResponse> getEvents(GetEventsParams params);

    EventFullDtoResponse updateEvent(Long eventId, UpdateEventAdminRequest event);

    @Builder
    @Data
    class GetEventsParams {
        private List<Long> users;
        private List<EventState> states;
        private List<Long> categories;
        private LocalDateTime rangeStart;
        private LocalDateTime rangeEnd;
        private int from;
        private int size;
    }
}