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
@RequestMapping("/admin/categories")
@RestController
@Slf4j
@Validated
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    @PostMapping
    public ResponseEntity<Object> addCategory(@RequestBody @Valid CategoryDtoRequest category) {
        log.info("POST /admin/categories body={}", category);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(adminCategoryService.addCategory(category));
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long catId) {
        log.info("DELETE /admin/categories/{}", catId);

        adminCategoryService.deleteCategory(catId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{catId}")
    public ResponseEntity<Object> updateCategory(@PathVariable Long catId,
                                                 @RequestBody @Valid CategoryDtoRequest category) {
        log.info("PATCH /admin/categories/{} body={}", catId, category);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(adminCategoryService.updateCategory(catId, category));
    }
}
