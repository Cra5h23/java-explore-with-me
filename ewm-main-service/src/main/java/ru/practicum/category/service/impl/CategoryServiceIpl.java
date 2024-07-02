package ru.practicum.category.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.category.service.CategoryService;
import ru.practicum.exception.NotFoundCategoryException;

/**
 * @author Nikolay Radzivon
 * @Date 25.06.2024
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceIpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category checkCategory(Long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundCategoryException(
                        String.format("Категория с id %d не существует или не доступна", catId)));
    }
}
