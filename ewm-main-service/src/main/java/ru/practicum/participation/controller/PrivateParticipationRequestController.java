package ru.practicum.participation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.participation.service.PrivateParticipationRequestService;

/**
 * @author Nikolay Radzivon
 * @Date 22.06.2024
 */
@RestController
@RequestMapping("/users/{userId}/requests")
@Slf4j
@RequiredArgsConstructor
public class PrivateParticipationRequestController {
    private final PrivateParticipationRequestService privateParticipationRequestService;

    @GetMapping
    public ResponseEntity<Object> getParticipationRequests(@PathVariable Long userId) {
        log.info("Получен запрос: GET /users/{}/requests", userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(privateParticipationRequestService.getParticipationRequests(userId));
    }

    @PostMapping
    public ResponseEntity<Object> addParticipationRequest(@PathVariable Long userId, @RequestParam Long eventId) {
        log.info("Получен запрос: POST /users/{}/requests?eventId={}", userId, eventId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(privateParticipationRequestService.addParticipationRequest(userId, eventId));
    }

    @PatchMapping("{requestId}/cancel")
    public ResponseEntity<Object> cancelParticipationRequest(@PathVariable Long userId, @PathVariable Long requestId) {
        log.info("Получен запрос: PATCH /users/{}/requests/{}/cancel", userId, requestId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(privateParticipationRequestService.cancelParticipationRequest(userId, requestId));
    }
}
