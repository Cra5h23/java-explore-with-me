package ru.practicum.location.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.location.dto.LocationDtoRequest;
import ru.practicum.location.service.AdminLocationService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author Nikolay Radzivon
 * @Date 07.07.2024
 */
@RestController
@RequestMapping("/admin/locations")
@RequiredArgsConstructor
@Slf4j
public class AdminLocationController {
    private final AdminLocationService adminLocationService;

    @PostMapping
    public ResponseEntity<Object> addLocation(@RequestBody @Valid LocationDtoRequest locationDto) {
        log.info("Получен запрос POST /admin/locations body={}", locationDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(adminLocationService.addLocation(locationDto));
    }

    @GetMapping("/{locId}")
    public ResponseEntity<Object> getLocation(@PathVariable @NotNull @Positive Long locId) {
        log.info("Получен запрос GET /admin/locations/{}",locId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(adminLocationService.getLocation(locId));
    }
}
