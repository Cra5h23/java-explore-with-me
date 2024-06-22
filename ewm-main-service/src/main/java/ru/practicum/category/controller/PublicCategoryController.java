package ru.practicum.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.category.service.PublicCategoryService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Nikolay Radzivon
 * @Date 22.06.2024
 */
@RequiredArgsConstructor
@RestController("/categories")
@Slf4j
public class PublicCategoryController {
    private PublicCategoryService publicCategoryService;

    @GetMapping
    public ResponseEntity<Object> getCategories(
            @RequestParam(required = false, defaultValue = "0")
            @Min(value = 0, message = "Параметр from не может быть меньше 0") long from,
            @RequestParam(required = false, defaultValue = "10")
            @Min(value = 1, message = "Параметр size не может быть меньше 1")
            @Max(value = 100, message = "Параметр size не может быть больше 100") long size
    ) {
        log.info("GET /categories?from={}&size={}", from, size);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(publicCategoryService.getCategories(from, size));

        // todo В случае, если по заданным фильтрам не найдено ни одной категории, возвращает пустой список
        //  400 код если запрос составлен некорректно.
    }

    @GetMapping("/{catId}")
    public ResponseEntity<Object> getCategory(
            @PathVariable Long catId
    ) {
        log.info("GET /categories/{}", catId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(publicCategoryService.getCategory(catId));

        // todo В случае, если категории с заданным id не найдено, возвращает статус код 404, 400
        //  Запрос составлен некорректно
    }
}
