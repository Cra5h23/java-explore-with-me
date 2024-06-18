package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.RequestHitDto;
import ru.practicum.service.StatsService;

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
    public void addHit(@RequestBody RequestHitDto requestHitDto) {
        log.info("POST /hit body={}", requestHitDto);
        statsService.saveHit(requestHitDto);
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam String start,
                                      @RequestParam String end,
                                      @RequestParam(required = false) List<String> uris,
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
}
