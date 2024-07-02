package ru.practicum.category.service;

import ru.practicum.dto.category.CategoryDtoRequest;
import ru.practicum.dto.category.CategoryDtoResponse;

/**
 * @author Nikolay Radzivon
 * @Date 19.06.2024
 */
public interface AdminCategoryService {
    CategoryDtoResponse addCategory(CategoryDtoRequest dto);

    void deleteCategory(Long catId);

    CategoryDtoResponse updateCategory(Long catId, CategoryDtoRequest dto);
}
