package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.UpdateEventAdminRequest;
import ru.practicum.event.service.AdminEventService;

import javax.validation.Valid;

/**
 * Контроллер для работы с событиями от имени администратора.
 *
 * @author Nikolay Radzivon
 * @Date 22.06.2024
 */
@RestController
@RequestMapping("/admin/events")
@Slf4j
@RequiredArgsConstructor
@Validated
public class AdminEventController {
    private final AdminEventService adminEventService;

    /**
     * Метод для работы
     */
    @GetMapping
    public ResponseEntity<Object> getEvents(@ModelAttribute @Valid AdminEventService.GetEventsParams params) {
        log.info("Получен запрос: GET /admin/events?users={}&states={}&categories={}&rangeStart={}&rangeEnd={}&from={}&size={}",
                params.getUsers(), params.getStates(), params.getCategories(), params.getRangeStart(),
                params.getRangeEnd(), params.getFrom(), params.getSize());

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(adminEventService.getEvents(params));
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<Object> updateEvent(
            @PathVariable Long eventId,
            @RequestBody @Valid UpdateEventAdminRequest event
    ) {
        log.info("Получен запрос: PATCH admin/events/{} body={}", eventId, event);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(adminEventService.updateEvent(eventId, event));
    }
}
