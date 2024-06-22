package ru.practicum.category.service;

import ru.practicum.dto.category.CategoryDtoResponse;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 22.06.2024
 */
public interface PublicCategoryService {
    List<CategoryDtoResponse> getCategories(long from, long size);

    CategoryDtoResponse getCategory(Long catId);
}
