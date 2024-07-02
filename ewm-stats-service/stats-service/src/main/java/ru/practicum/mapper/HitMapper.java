package ru.practicum.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.practicum.dto.RequestHitDto;
import ru.practicum.dto.ResponseStatsDto;
import ru.practicum.model.Hit;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static ru.practicum.repository.StatsRepository.CountHits;

/**
 * Маппер для преобразования Dto {@link RequestHitDto} в сущность {@link Hit} и сущность {@link CountHits} в Dto {@link ResponseStatsDto}.
 *
 * @author Nikolay Radzivon
 * @Date 14.06.2024
 */
@Component
public class HitMapper {
    @Value("${datetime.format:yyyy-MM-dd HH:mm:ss}")
    private String dateTimeFormat;

    /**
     * Метод преобразования Dto {@link RequestHitDto} в сущность {@link Hit}.
     *
     * @param dto данные посещения.
     * @return объект {@link Hit}, представляющий сущность посещения.
     */
    public Hit toHit(RequestHitDto dto) {
        var parse = LocalDateTime.parse(dto.getTimestamp(),
                        DateTimeFormatter.ofPattern(dateTimeFormat))
                .atZone(ZoneId.systemDefault());

        return Hit.builder()
                .app(dto.getApp())
                .uri(dto.getUri())
                .timestamp(parse)
                .ip(dto.getIp())
                .build();
    }

    /**
     * Метод преобразования сущности {@link CountHits} в Dto {@link ResponseStatsDto}.
     *
     * @param hits данные о количестве посещений
     * @return объект {@link ResponseStatsDto}, содержащий данные количестве посещений.
     */
    public ResponseStatsDto toResponseStatsDto(CountHits hits) {
        return ResponseStatsDto.builder()
                .app(hits.getApp())
                .hits(hits.getCount())
                .uri(hits.getUri())
                .build();
    }
}
