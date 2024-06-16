package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.RequestStatsDto;
import ru.practicum.service.StatsService;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 14.06.2024
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void addStats(@RequestBody RequestStatsDto requestStatsDto) {
        log.info("POST /hit body={}", requestStatsDto);
        statsService.saveStats(requestStatsDto);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(@RequestParam String start,
                                           @RequestParam String end,
                                           @RequestParam(required = false) List<String> uris,
                                           @RequestParam(required = false, defaultValue = "false") boolean unique) {

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(statsService.getStats(StatsService.Params.builder()
                        .start(start)
                        .end(end)
                        .unique(unique)
                        .uris(uris)
                        .build()));
    }
}
