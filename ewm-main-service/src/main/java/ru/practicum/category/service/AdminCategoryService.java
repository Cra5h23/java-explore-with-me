package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDtoRequest;
import ru.practicum.category.dto.CategoryDtoResponse;

/**
 * @author Nikolay Radzivon
 * @Date 19.06.2024
 */
public interface AdminCategoryService {
    CategoryDtoResponse addCategory(CategoryDtoRequest dto);

    void deleteCategory(Long catId);

    CategoryDtoResponse updateCategory(Long catId, CategoryDtoRequest dto);
}
