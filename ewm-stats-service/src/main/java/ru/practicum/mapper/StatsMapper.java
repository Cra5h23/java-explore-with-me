package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.dto.RequestStatsDto;
import ru.practicum.dto.ResponseStatsDto;
import ru.practicum.model.Stats;

import static ru.practicum.repository.StatsRepository.CountStats;

/**
 * @author Nikolay Radzivon
 * @Date 14.06.2024
 */
@Component
public class StatsMapper {

    public Stats toStats(RequestStatsDto dto) {
        return Stats.builder()
                .app(dto.getApp())
                .uri(dto.getUri())
                .timestamp(dto.getTimestamp())
                .ip(dto.getIp())
                .build();
    }

    public ResponseStatsDto toResponseStatsDto(CountStats stats) {
        return ResponseStatsDto.builder()
                .app(stats.getApp())
                .hits(stats.getCount())
                .uri(stats.getUri())
                .build();
    }
}
