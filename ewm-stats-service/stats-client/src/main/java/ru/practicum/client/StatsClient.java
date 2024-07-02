package ru.practicum.client;

import org.springframework.http.ResponseEntity;
import ru.practicum.dto.ResponseStatsDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 16.06.2024
 */
public interface StatsClient {

    void saveStats(String ip, String uri, String appName);

    ResponseEntity<List<ResponseStatsDto>> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
