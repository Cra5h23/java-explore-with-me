package ru.practicum.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.service.PublicCategoryService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Nikolay Radzivon
 * @Date 22.06.2024
 */
@RequiredArgsConstructor
@RequestMapping("/categories")
@RestController
@Slf4j
public class PublicCategoryController {
    private final PublicCategoryService publicCategoryService;

    @GetMapping
    public ResponseEntity<Object> getCategories(
            @RequestParam(required = false, defaultValue = "0")
            @Min(value = 0, message = "Параметр from не может быть меньше 0") int from,
            @RequestParam(required = false, defaultValue = "10")
            @Min(value = 1, message = "Параметр size не может быть меньше 1")
            @Max(value = 100, message = "Параметр size не может быть больше 100") int size
    ) {
        log.info("Получен запрос: GET /categories?from={}&size={}", from, size);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(publicCategoryService.getCategories(from, size));
    }

    @GetMapping("/{catId}")
    public ResponseEntity<Object> getCategory(@PathVariable Long catId) {
        log.info("Получен запрос: GET /categories/{}", catId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(publicCategoryService.getCategory(catId));
    }
}
