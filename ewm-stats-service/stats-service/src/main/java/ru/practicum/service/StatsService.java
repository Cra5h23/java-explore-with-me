package ru.practicum.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.dto.RequestHitDto;
import ru.practicum.dto.ResponseStatsDto;
import ru.practicum.validator.ValidDateRange;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 14.06.2024
 */
public interface StatsService {
    void saveHit(RequestHitDto dto);

    List<ResponseStatsDto> getStats(Params params);

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ValidDateRange
    class Params {
        @NotNull(message = "Параметр start должен быть указан")
        private LocalDateTime start;
        @NotNull(message = "Параметр end должен быть указан")
        private LocalDateTime end;
        private List<String> uris;
        @Builder.Default
        private boolean unique = false;
    }
}

