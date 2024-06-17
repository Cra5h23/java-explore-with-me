package ru.practicum.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.dto.RequestStatsDto;
import ru.practicum.dto.ResponseStatsDto;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 14.06.2024
 */
public interface StatsService {
    void saveStats(RequestStatsDto dto);

    List<ResponseStatsDto> getStats(Params params);

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Params {
        private String start;
        private String end;
        private List<String> uris;
        private boolean unique;
    }
}

