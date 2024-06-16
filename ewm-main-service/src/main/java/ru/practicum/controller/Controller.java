package ru.practicum.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.servise.StatsService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 15.06.2024
 */
@RestController
@RequestMapping("/events")
@Slf4j
@RequiredArgsConstructor
public class Controller {
    private final StatsService statsService;
    private String app = "ewm-main-service";

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void getEvents(HttpServletRequest request) {
        log.info("GET /events");
        String remoteAddr = request.getRemoteAddr();
        String requestURI = request.getRequestURI();

        statsService.saveStats(remoteAddr, requestURI, app);
    }

    @GetMapping("/{id}")
    public void getEvent(HttpServletRequest request, @PathVariable Long id) {
        log.info("GET /events/{}", id);

        String requestURI = request.getRequestURI();
        String remoteAddr = request.getRemoteAddr();

        statsService.saveStats(remoteAddr, requestURI, app);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(@RequestParam @JsonFormat(timezone = "yyyy-MM-dd HH:mm:ss") String start,
                                           @RequestParam @JsonFormat(timezone = "yyyy-MM-dd HH:mm:ss") String end,
                                           @RequestParam(required = false) List<URI> uris,
                                           @RequestParam(required = false) Boolean unique) {
        log.info("GET /stats");
        return statsService.getStats(start, end, uris, unique);
    }
}
