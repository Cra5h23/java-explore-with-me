package ru.practicum.location.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.location.dto.EventSortType;
import ru.practicum.location.service.PublicLocationService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author Nikolay Radzivon
 * @Date 07.07.2024
 */
@Slf4j
@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
@Validated
public class PublicLocationController {
    private final PublicLocationService publicLocationService;

    @GetMapping
    public ResponseEntity<Object> getLocations(@ModelAttribute @Valid PublicLocationService.GetLocationsParams params) {
        log.info("Получен запрос: GET /locations?from={}&size={}", params.getFrom(), params.getSize());

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(publicLocationService.getLocations(params));
    }

    @GetMapping("/{locId}")
    public ResponseEntity<Object> getLocation(@PathVariable @NotNull @Positive Long locId,
                                              @RequestParam(required = false, defaultValue = "upcoming") EventSortType eventStatus) {
        log.info("Получен запрос: GET /locations/{}?eventStatus={}", locId, eventStatus);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(publicLocationService.getLocation(locId, eventStatus));
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchLocations(@ModelAttribute @Valid PublicLocationService.SearchParams params) {
        log.info("Получен запрос: GET /locations?from={}&size={}&lon={}&lan={}&radius={}&text={}",
                params.getFrom(), params.getSize(), params.getLon(), params.getLat(), params.getRadius(), params.getText());

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(publicLocationService.searchLocations(params));
    }
}
