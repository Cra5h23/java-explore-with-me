package ru.practicum.category.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.category.service.AdminCategoryService;
import ru.practicum.category.service.CategoryService;
import ru.practicum.category.dto.CategoryDtoRequest;
import ru.practicum.category.dto.CategoryDtoResponse;

/**
 * Сервис для управления категориями от имени администратора.
 *
 * @author Nikolay Radzivon
 * @Date 20.06.2024
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryChecker;

    /**
     * Метод добавления новой категории на основе переданного DTO.
     *
     * @param dto DTO категории для добавления
     * @return DTO ответа с данными добавленной категории
     */
    @Override
    public CategoryDtoResponse addCategory(CategoryDtoRequest dto) {
        var category = categoryMapper.toCategory(dto);
        var save = categoryRepository.save(category);
        log.info("Добавлена новая категория {}", save);

        return categoryMapper.toCategoryDtoResponse(save);
    }

    /**
     * Метод удаления категорию по идентификационному номеру.
     *
     * @param catId Идентификационный номер категории для удаления
     */
    @Override
    public void deleteCategory(Long catId) {
        var category = categoryChecker.checkCategory(catId);

        categoryRepository.delete(category);
        log.info("Удалена категория с id {}", catId);
    }

    /**
     * Метод обновления данных категории на основе переданного DTO.
     *
     * @param catId Идентификационный номер категории для обновления
     * @param dto   DTO категории с новыми данными
     * @return DTO ответа с обновленными данными категории
     */
    @Override
    public CategoryDtoResponse updateCategory(Long catId, CategoryDtoRequest dto) {
        log.info("Запрос на обновление категории с id {}, новые данные {}", catId, dto);
        var category = categoryChecker.checkCategory(catId);
        log.info("Старые данные {}", category);

        if (dto.getName() != null) {
            category.setName(dto.getName());
        }

        var save = categoryRepository.save(category);

        return categoryMapper.toCategoryDtoResponse(save);
    }
}
