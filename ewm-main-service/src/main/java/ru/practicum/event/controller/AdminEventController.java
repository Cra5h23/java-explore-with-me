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
    public ResponseEntity<Object> getEvents(
//            @RequestParam(required = false) List<Long> users,
//            @RequestParam(required = false) List<EventState> states,
//            @RequestParam(required = false) List<Long> categories,
//            @RequestParam(required = false) LocalDateTime rangeStart,
//            @RequestParam(required = false) LocalDateTime rangeEnd,
//            @RequestParam(required = false, defaultValue = "0") @Valid
//            @Min(value = 0, message = "Параметр from не может быть меньше {value}") int from,
//            @RequestParam(required = false, defaultValue = "10")
//            @Min(value = 1, message = "Параметр size не может быть меньше {value}") @Valid
//            @Max(value = 1000, message = "Параметр size не может быть больше {value}") int size

            @ModelAttribute @Valid AdminEventService.GetEventsParams params
    ) {
        log.info("Получен запрос: GET /admin/events?users={}&states={}&categories={}&rangeStart={}&rangeEnd={}&from={}&size={}",
                params.getUsers(), params.getStates(), params.getCategories(), params.getRangeStart(), params.getRangeEnd(), params.getFrom(), params.getSize());
//        log.info("GET /admin/events?users={}&states={}&categories={}&rangeStart={}&rangeEnd={}&from={}&size={}",
//                users, states, categories, rangeStart, rangeEnd, from, size);

//        var params = AdminEventService.GetEventsParams.builder()
//                .users(users)
//                .states(states)
//                .categories(categories)
//                .rangeStart(rangeStart)
//                .rangeEnd(rangeEnd)
//                .from(from)
//                .size(size)
//                .build();

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(adminEventService.getEvents(params));

        //todo Эндпоинт возвращает полную информацию обо всех событиях подходящих под переданные условия
        //  В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<Object> updateEvent(
            @PathVariable Long eventId,
            @RequestBody @Valid UpdateEventAdminRequest event
    ) {
        log.info("PATCH admin/events/{} body={}", eventId, event);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(adminEventService.updateEvent(eventId, event));

        //todo Редактирование данных любого события администратором. Валидация данных не требуется. Обратите внимание:
        // дата начала изменяемого события должна быть не ранее чем за час от даты публикации. (Ожидается код ошибки 409)
        // событие можно публиковать, только если оно в состоянии ожидания публикации (Ожидается код ошибки 409)
        // событие можно отклонить, только если оно еще не опубликовано (Ожидается код ошибки 409)
    }
}
