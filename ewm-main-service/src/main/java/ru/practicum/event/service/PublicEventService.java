package ru.practicum.event.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.event.dto.EventFullDtoResponse;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.EventSort;
import ru.practicum.validator.ValidDateRange;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 22.06.2024
 */
public interface PublicEventService {
    List<EventShortDto> getEvents(GetEventsParams params, HttpServletRequest request);

    EventFullDtoResponse getEvent(Long id, HttpServletRequest request);

    @Builder
    @Data
    @ValidDateRange
    @NoArgsConstructor
    @AllArgsConstructor
    class GetEventsParams {
        private String text;
        private List<Long> categories;
        private Boolean paid;
        private LocalDateTime rangeStart;
        private LocalDateTime rangeEnd;
        private Boolean onlyAvailable;
        private EventSort sort;
        @Min(value = 0, message = "Параметр from не может быть меньше {value}")
        @Builder.Default
        private int from = 0;
        @Min(value = 1, message = "Параметр size не может быть меньше {value}")
        @Max(value = 1000, message = "Параметр size не может быть больше {value}")
        @Builder.Default
        private int size = 10;

//        {
//            this.from = 0;
//            this.size = 10;
//        }
    }
}
