package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.RequestHitDto;
import ru.practicum.service.StatsService;

import javax.validation.Valid;

/**
 * Контроллер для работы Stats-service
 *
 * @author Nikolay Radzivon
 * @Date 14.06.2024
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
public class StatsController {
    private final StatsService statsService;

    /**
     * Метод для эндпоинта POST /hit. Сохранение информации о том, что на uri конкретного сервиса был отправлен запрос пользователем. Название сервиса, uri и ip пользователя указаны в теле запроса.
     *
     * @param requestHitDto {@link RequestHitDto} данные посещения.
     */
    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void addHit(@RequestBody RequestHitDto requestHitDto) {
        log.info("POST /hit body={}", requestHitDto);
        statsService.saveHit(requestHitDto);
    }

    /**
     * Метод для эндпоинта GET /stats. Получение статистики посещения.
     *
     * @param start  Дата и время начала диапазона за который нужно выгрузить статистику.
     * @param end    Дата и время конца диапазона за который нужно выгрузить статистику.
     * @param uris   список uri для которых нужно получить статистику.
     * @param unique учитывать уникальные посещения.
     * @return список посещений.
     */
    @GetMapping("/stats")
    public ResponseEntity<?> getStats(@ModelAttribute @Valid StatsService.Params params) {
        log.info("GET /stats?start={}&end={}&uris={}&unique={}", params.getStart(), params.getEnd(),
                params.getUris(), params.isUnique());
//        var startDecode = URLDecoder.decode(start, StandardCharsets.UTF_8);
//        var endDecode = URLDecoder.decode(end, StandardCharsets.UTF_8);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(statsService.getStats(params));
    }
}
