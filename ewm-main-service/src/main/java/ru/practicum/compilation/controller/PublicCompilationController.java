package ru.practicum.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.service.PublicCompilationService;

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
            @RequestParam(required = false, defaultValue = "0") int from,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        log.info("Получен запрос: GET /compilations?pinned={}&from={}&size={}", pinned, from, size);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(publicCompilationService.getCompilations(pinned, from, size));
    }

    @GetMapping("/{compId}")
    public ResponseEntity<Object> getCompilation(@PathVariable Long compId) {
        log.info("Получен запрос: GET /compilations/{}", compId);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(publicCompilationService.getCompilation(compId));
    }
}
