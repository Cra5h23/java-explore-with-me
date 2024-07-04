package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventRequestStatusUpdateRequest;
import ru.practicum.event.dto.EventDtoRequest;
import ru.practicum.event.dto.UpdateEventUserRequest;
import ru.practicum.event.service.PrivateEventService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Nikolay Radzivon
 * @Date 20.06.2024
 */
@RestController
@RequestMapping("/users/{userId}/events")
@Slf4j
@RequiredArgsConstructor
@Validated
public class PrivateEventController {
    private final PrivateEventService privateEventService;

    @GetMapping
    public ResponseEntity<Object> getUserEvents(
            @PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "0")
            @Min(value = 0, message = "Параметр from не может быть меньше {value}") int from,
            @RequestParam(required = false, defaultValue = "10")
            @Min(value = 1, message = "Параметр size не может быть меньше {value}")
            @Max(value = 1000, message = "Параметр size не может быть больше {value}") int size
    ) {
        log.info("Получен запрос: GET /users/{}/events?from={}&size={}", userId, from, size);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(privateEventService.getUserEvents(userId, from, size));
    }

    @PostMapping
    public ResponseEntity<Object> addEvent(@PathVariable Long userId, @RequestBody @Valid EventDtoRequest event) {
        log.info("Получен запрос: POST /users/{}/events body={}", userId, event);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(privateEventService.addEvent(userId, event));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Object> getUserEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Получен запрос: GET /users/{}/events/{}", userId, eventId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(privateEventService.getUserEvent(userId, eventId));
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<Object> updateUserEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody @Validated UpdateEventUserRequest event
    ) {
        log.info("Получен запрос: PATCH /users/{}/events/{} body={}", userId, eventId, event);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(privateEventService.updateUserEvent(userId, eventId, event));
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<Object> getEventRequests(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Получен запрос: GET /users/{}/events/{}/requests", userId, eventId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(privateEventService.getEventRequests(userId, eventId));
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<Object> confirmUserRequests(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody(required = false) EventRequestStatusUpdateRequest request
    ) {
        log.info("Получен запрос: PATCH /users/{}/events/{}/requests body={}", userId, eventId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(privateEventService.confirmUserRequests(userId, eventId, request));
    }
}
