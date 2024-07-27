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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
    public ResponseEntity<Object> getEvent(@PathVariable @NotNull @Positive Long id, HttpServletRequest request) {
        log.info("Получен запрос: GET /events/{}", id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(publicEventService.getEvent(id, request));
    }

    @GetMapping("/locations")
    public ResponseEntity<Object> searchEventsByLocation(@ModelAttribute @Valid PublicEventService.GetSearchParams params) {
        log.info("Получен запрос: GET /events/locations?from={}&size={}&lat={}&lon={}&radius={}&eventStatus={}",
                params.getFrom(), params.getSize(), params.getLat(), params.getLon(), params.getRadius(), params.getEventStatus());

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(publicEventService.searchEventsByLocation(params));
    }
}

