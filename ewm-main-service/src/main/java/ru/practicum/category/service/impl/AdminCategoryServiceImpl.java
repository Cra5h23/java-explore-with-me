package ru.practicum.category.service.impl;

import org.springframework.stereotype.Service;
import ru.practicum.category.service.AdminCategoryService;
import ru.practicum.dto.category.CategoryDtoRequest;
import ru.practicum.dto.category.CategoryDtoResponse;

/**
 * @author Nikolay Radzivon
 * @Date 20.06.2024
 */
@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {
    /**
     * @param category
     * @return
     */
    @Override
    public CategoryDtoResponse addCategory(CategoryDtoRequest category) {
        return null;
    }

    /**
     * @param catId
     */
    @Override
    public void deleteCategory(Long catId) {

    }

    /**
     * @param catId
     * @param category
     * @return
     */
    @Override
    public CategoryDtoResponse updateCategory(Long catId, CategoryDtoRequest category) {
        return null;
    }
}
