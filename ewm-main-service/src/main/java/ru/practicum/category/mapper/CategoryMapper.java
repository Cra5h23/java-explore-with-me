package ru.practicum.category.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.category.model.Category;
import ru.practicum.dto.category.CategoryDtoRequest;
import ru.practicum.dto.category.CategoryDtoResponse;

/**
 * @author Nikolay Radzivon
 * @Date 25.06.2024
 */
@Component
public class CategoryMapper {
    public Category toCategory(CategoryDtoRequest category) {
        return Category.builder()
                .name(category.getName())
                .build();
    }

    public CategoryDtoResponse toCategoryDtoResponse(Category category) {
        return CategoryDtoResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
