package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.practicum.dto.RequestStatsDto;
import ru.practicum.service.StatsService;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
    public ResponseEntity<?> getStats(@RequestParam String start,
                                      @RequestParam String end,
                                      @RequestParam(required = false) List<URI> uris,
                                      @RequestParam(required = false, defaultValue = "false") boolean unique
    ) {
        log.info("GET /stats?start={}&end={}&uris={}&unique={}", start, end, uris, unique);
        var startDecode = URLDecoder.decode(start, StandardCharsets.UTF_8);
        var endDecode = URLDecoder.decode(end, StandardCharsets.UTF_8);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(statsService.getStats(StatsService.Params.builder()
                        .start(startDecode)
                        .end(endDecode)
                        .unique(unique)
                        .uris(uris)
                        .build()));
    }

    @GetMapping
    public ResponseEntity<?> test() {
        log.info("Привет");
        return ResponseEntity.ok()
                .body("");
    }
}
