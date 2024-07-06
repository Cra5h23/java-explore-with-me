package ru.practicum.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDtoRequest;
import ru.practicum.category.service.AdminCategoryService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author Nikolay Radzivon
 * @Date 19.06.2024
 */
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
@RestController
@Slf4j
@Validated
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    @PostMapping
    public ResponseEntity<Object> addCategory(@RequestBody @Valid CategoryDtoRequest category) {
        log.info("Получен запрос: POST /admin/categories body={}", category);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(adminCategoryService.addCategory(category));
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable @NotNull @Positive Long catId) {
        log.info("Получен запрос: DELETE /admin/categories/{}", catId);

        adminCategoryService.deleteCategory(catId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{catId}")
    public ResponseEntity<Object> updateCategory(@PathVariable @NotNull @Positive Long catId,
                                                 @RequestBody @Valid CategoryDtoRequest category) {
        log.info("Получен запрос: PATCH /admin/categories/{} body={}", catId, category);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(adminCategoryService.updateCategory(catId, category));
    }
}
