package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.StatsClient;

import javax.servlet.http.HttpServletRequest;


/**
 * @author Nikolay Radzivon
 * @Date 15.06.2024
 */
@RestController
@RequestMapping("/events")
@Slf4j
@RequiredArgsConstructor
public class PublicEventController {
    private final StatsClient statsClient;
    private String appName = "ewm-main-service";

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void getEvents(HttpServletRequest request) {
        log.info("GET /events");

        statsClient.saveStats(request, appName);
    }

    @GetMapping("/{id}")
    public void getEvent(HttpServletRequest request, @PathVariable Long id) {
        log.info("GET /events/{}", id);

        statsClient.saveStats(request, appName);
    }
}
