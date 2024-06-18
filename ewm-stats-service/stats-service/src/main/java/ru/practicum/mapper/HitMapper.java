package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.dto.RequestHitDto;
import ru.practicum.dto.ResponseStatsDto;
import ru.practicum.model.Hit;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static ru.practicum.repository.StatsRepository.CountHits;

/**
 * @author Nikolay Radzivon
 * @Date 14.06.2024
 */
@Component
public class HitMapper {

    public Hit toHit(RequestHitDto dto) {
        var parse = LocalDateTime.parse(dto.getTimestamp(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .atZone(ZoneId.systemDefault());

        return Hit.builder()
                .app(dto.getApp())
                .uri(dto.getUri())
                .timestamp(parse)
                .ip(dto.getIp())
                .build();
    }

    public ResponseStatsDto toResponseStatsDto(CountHits hits) {
        return ResponseStatsDto.builder()
                .app(hits.getApp())
                .hits(hits.getCount())
                .uri(hits.getUri())
                .build();
    }
}
