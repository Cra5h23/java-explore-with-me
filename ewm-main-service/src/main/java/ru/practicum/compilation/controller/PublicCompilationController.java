package ru.practicum.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.service.PublicCompilationService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author Nikolay Radzivon
 * @Date 19.06.2024
 */
@RestController
@RequestMapping("/compilations")
@Slf4j
@RequiredArgsConstructor
public class PublicCompilationController {
    private final PublicCompilationService publicCompilationService;

    @GetMapping
    public ResponseEntity<Object> getCompilations(
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(required = false, defaultValue = "0")
            @Min(value = 0, message = "Параметр from не может быть меньше {value}") int from,
            @RequestParam(required = false, defaultValue = "10")
            @Min(value = 1, message = "Параметр size не может быть меньше {value}")
            @Max(value = 100, message = "Параметр size не может быть больше {value}") int size
    ) {
        log.info("Получен запрос: GET /compilations?pinned={}&from={}&size={}", pinned, from, size);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(publicCompilationService.getCompilations(pinned, from, size));
    }

    @GetMapping("/{compId}")
    public ResponseEntity<Object> getCompilation(@PathVariable @NotNull @Positive Long compId) {
        log.info("Получен запрос: GET /compilations/{}", compId);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(publicCompilationService.getCompilation(compId));
    }
}
