package ru.practicum.location.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.location.service.PublicLocationService;

import javax.validation.Valid;

/**
 * @author Nikolay Radzivon
 * @Date 07.07.2024
 */
@Slf4j
@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
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
}
