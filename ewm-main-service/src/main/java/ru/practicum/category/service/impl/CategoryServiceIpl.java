package ru.practicum.category.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@Slf4j
public class CategoryServiceIpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public Category checkCategory(Long catId) {
        log.info("Проверка на то что категория с id {} существует", catId);
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundCategoryException(
                        String.format("Категория с id %d не существует или не доступна", catId)));
    }
}
