package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDtoResponse;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 22.06.2024
 */
public interface PublicCategoryService {
    List<CategoryDtoResponse> getCategories(int from, int size);

    CategoryDtoResponse getCategory(Long catId);
}
