package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.service.PublicEventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Nikolay Radzivon
 * @Date 15.06.2024
 */
@RestController
@RequestMapping("/events")
@Slf4j
@RequiredArgsConstructor
@Validated
public class PublicEventController {
    private final PublicEventService publicEventService;

    /**
     * Метод получения событий с возможностью фильтрации.
     *
     * @param request
     */
    @GetMapping
    public ResponseEntity<Object> getEvents(@ModelAttribute @Valid PublicEventService.GetEventsParams params,
                                            HttpServletRequest request) {
        log.info("Получен запрос: GET /events?text={}&categories={}&paid={}&rangeStart={}&rangeEnd={}&onlyAvailable={}&sort={}&from={}&size={}",
                params.getText(), params.getCategories(), params.getPaid(), params.getRangeStart(),
                params.getRangeStart(), params.getOnlyAvailable(), params.getSort(), params.getFrom(), params.getSize());

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(publicEventService.getEvents(params, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEvent(@PathVariable Long id, HttpServletRequest request) {
        log.info("Получен запрос: GET /events/{}", id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(publicEventService.getEvent(id, request));
    }
}

