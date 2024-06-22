package ru.practicum.category.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.category.service.PublicCategoryService;
import ru.practicum.dto.category.CategoryDtoResponse;

import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 22.06.2024
 */
@Service
@Slf4j
public class PublicCategoryServiceImpl implements PublicCategoryService {
    /**
     * @param from
     * @param size
     * @return
     */
    @Override
    public List<CategoryDtoResponse> getCategories(long from, long size) {
        return List.of();
    }

    /**
     * @param catId
     * @return
     */
    @Override
    public CategoryDtoResponse getCategory(Long catId) {
        return null;
    }
}
