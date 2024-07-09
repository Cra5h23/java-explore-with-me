package ru.practicum.location.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Marker;
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
@Validated
public class AdminLocationController {
    private final AdminLocationService adminLocationService;

    @PostMapping
    @Validated(Marker.OnCreate.class)
    public ResponseEntity<Object> addLocation(@RequestBody @Valid LocationDtoRequest locationDto) {
        log.info("Получен запрос: POST /admin/locations body={}", locationDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(adminLocationService.addLocation(locationDto));
    }

    @PatchMapping("/{locId}")
    @Validated(Marker.OnUpdate.class)
    public ResponseEntity<Object> updateLocation(@RequestBody @Valid LocationDtoRequest locationDto,
                                                 @PathVariable @NotNull @Positive Long locId) {
        log.info("Получен запрос: PATCH /admin/locations/{} body={}", locId, locationDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(adminLocationService.updateLocation(locId, locationDto));
    }

    @DeleteMapping("/{locId}")
    public ResponseEntity<Object> deleteLocation(@PathVariable @NotNull @Positive Long locId) {
        log.info("Получен запрос: DELETE /admin/locations/{}", locId);

        adminLocationService.deleteLocation(locId);
        return ResponseEntity.noContent().build();
    }
}
