package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.dto.RequestStatsDto;
import ru.practicum.dto.ResponseStatsDto;
import ru.practicum.model.Stats;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static ru.practicum.repository.StatsRepository.CountStats;

/**
 * @author Nikolay Radzivon
 * @Date 14.06.2024
 */
@Component
public class StatsMapper {

    public Stats toStats(RequestStatsDto dto) {
        var parse = LocalDateTime.parse(dto.getTimestamp(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .atZone(ZoneId.systemDefault());

        return Stats.builder()
                .app(dto.getApp())
                .uri(dto.getUri())
                .timestamp(parse)
                // .timestamp(dto.getTimestamp())
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
