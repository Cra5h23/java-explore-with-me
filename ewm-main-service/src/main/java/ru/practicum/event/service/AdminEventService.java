package ru.practicum.event.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.event.dto.EventFullDtoResponse;
import ru.practicum.event.dto.EventState;
import ru.practicum.event.dto.UpdateEventAdminRequest;
import ru.practicum.validator.ValidDateRange;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    @ValidDateRange
    @NoArgsConstructor
    @AllArgsConstructor
    class GetEventsParams {
        private List<Long> users;
        private List<EventState> states;
        private List<Long> categories;
        private LocalDateTime rangeStart;
        private LocalDateTime rangeEnd;
        @Min(value = 0, message = "Параметр from не может быть меньше {value}")
        @Builder.Default
        private int from = 0;
        @Min(value = 1, message = "Параметр size не может быть меньше {value}")
        @Max(value = 1000, message = "Параметр size не может быть больше {value}")
        @Builder.Default
        private int size = 10;
    }
}
