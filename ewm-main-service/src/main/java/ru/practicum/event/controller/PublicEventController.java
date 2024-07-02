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
    private final String appName = "ewm-main-service";
    private final PublicEventService publicEventService;

    /**
     * Метод получения событий с возможностью фильтрации.
     *
     * @param request
     */
    @GetMapping
    public ResponseEntity<Object> getEvents(
//            @RequestParam(required = false) String text,
//            @RequestParam(required = false) List<Long> categories,
//            @RequestParam(required = false) Boolean paid,
//            @RequestParam(required = false) LocalDateTime rangeStart,
//            @RequestParam(required = false) LocalDateTime rangeEnd,
//            @RequestParam(required = false) Boolean onlyAvailable,
//            @RequestParam(required = false) EventSort sort,
//            @RequestParam(required = false, defaultValue = "0") @Valid
//            @Min(value = 0, message = "Параметр from не может быть меньше {value}") int from,
//            @RequestParam(required = false, defaultValue = "10") @Valid
//            @Min(value = 1, message = "Параметр size не может быть меньше {value}")
//            @Max(value = 1000, message = "Параметр size не может быть больше {value}") int size,
//            HttpServletRequest request
            @ModelAttribute @Valid PublicEventService.GetEventsParams params,
            HttpServletRequest request
    ) {
//        log.info("GET /events?text={}&categories={}&paid={}&rangeStart={}&rangeEnd={}&onlyAvailable={}&sort={}&from={}&size={}",
//                text,categories,paid,rangeStart,rangeEnd,onlyAvailable, sort, from, size);
        log.info("GET /events?text={}&categories={}&paid={}&rangeStart={}&rangeEnd={}&onlyAvailable={}&sort={}&from={}&size={}",
                params.getText(), params.getCategories(), params.getPaid(), params.getRangeStart(), params.getRangeStart(), params.getOnlyAvailable(), params.getSort(), params.getFrom(), params.getSize());
//
//        var params = PublicEventService.GetEventsParams.builder()
//                .text(text)
//                .categories(categories)
//                .paid(paid)
//                .rangeStart(rangeStart)
//                .rangeEnd(rangeEnd)
//                .onlyAvailable(onlyAvailable)
//                .sort(sort)
//                .from(from)
//                .size(size)
//                .request(request)
//                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(publicEventService.getEvents(params, request));

        // todo Обратите внимание:
        //  это публичный эндпоинт, соответственно в выдаче должны быть только опубликованные события
        //  текстовый поиск (по аннотации и подробному описанию) должен быть без учета регистра букв
        //  если в запросе не указан диапазон дат [rangeStart-rangeEnd], то нужно выгружать события, которые произойдут позже текущей даты и времени
        //  информация о каждом событии должна включать в себя количество просмотров и количество уже одобренных заявок на участие
        //  информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики
        //  В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEvent(HttpServletRequest request, @PathVariable Long id) {
        log.info("GET /events/{}", id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(publicEventService.getEvent(id, request));

        // todo Обратите внимание:
        //  событие должно быть опубликовано
        //  информация о событии должна включать в себя количество просмотров и количество подтвержденных запросов
        //  информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики
        //  В случае, если события с заданным id не найдено, возвращает статус код 404
    }
}

