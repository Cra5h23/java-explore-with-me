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
    public ResponseEntity<Object> getParticipationRequests(
            @PathVariable Long userId
    ) {
        log.info("GET /users/{}/requests", userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(privateParticipationRequestService.getParticipationRequests(userId));

        //todo В случае, если по заданным фильтрам не найдено ни одной заявки, возвращает пустой список
        //  400 если запрос составлен некоректно
        //  404 если пользователь не найден
    }

    @PostMapping
    public ResponseEntity<Object> addParticipationRequest(
            @PathVariable Long userId,
            @RequestParam Long eventId
    ) {
        log.info("POST /users/{}/requests?eventId={}", userId, eventId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(privateParticipationRequestService.addParticipationRequest(userId, eventId));

        //todo Обратите внимание:
        // нельзя добавить повторный запрос (Ожидается код ошибки 409)
        // инициатор события не может добавить запрос на участие в своём событии (Ожидается код ошибки 409)
        // нельзя участвовать в неопубликованном событии (Ожидается код ошибки 409)
        // если у события достигнут лимит запросов на участие - необходимо вернуть ошибку (Ожидается код ошибки 409)
        // если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в состояние подтвержденного
    }

    @PatchMapping("{requestId}/cancel")
    public ResponseEntity<Object> cancelParticipationRequest(
            @PathVariable Long userId,
            @PathVariable Long requestId
    ) {
        log.info("PATCH /users/{}/requests/{}/cancel", userId, requestId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(privateParticipationRequestService.cancelParticipationRequest(userId, requestId));

        //todo
    }
}
