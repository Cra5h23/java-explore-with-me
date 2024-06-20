package ru.practicum.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.service.AdminCategoryService;
import ru.practicum.dto.category.CategoryDtoRequest;

import javax.validation.Valid;

/**
 * @author Nikolay Radzivon
 * @Date 19.06.2024
 */
@RequiredArgsConstructor
@RestController("/admin/categories")
@Slf4j
@Validated
public class AdminCategoryController {
    private AdminCategoryService adminCategoryService;

    @PostMapping
    public ResponseEntity<Object> addCategory(@RequestBody @Valid CategoryDtoRequest category) {
        log.info("POST /admin/categories body={}", category);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(adminCategoryService.addCategory(category));

        //todo должен быть 400 код когда не прошла валидацию дто, 409 код когда имя категории не уникально(добавить в бд то что поле name должно быть уникальным)
    }

    @DeleteMapping("/{catId:\\d+}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long catId) {
        log.info("DELETE /admin/categories/{}", catId);

        adminCategoryService.deleteCategory(catId);
        return ResponseEntity.noContent().build();

        //todo должен возвращать 404 когда категория не найдена и 409 когда с категорией связано хотя бы одно событие.
    }

    @PatchMapping("/{catId:\\d+}")
    public ResponseEntity<Object> updateCategory(@PathVariable Long catId, @RequestBody @Valid CategoryDtoRequest category) {
        log.info("PATCH /admin/categories/{} body={}", catId, category);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(adminCategoryService.updateCategory(catId, category));

        //todo должен быть 400 код когда не прошлла валидацию дто, 409 код когда имя категории не уникально(добавить в бд то что поле name должно быть уникальным)
    }
}
