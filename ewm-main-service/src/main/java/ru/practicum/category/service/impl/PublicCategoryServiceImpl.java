package ru.practicum.category.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.category.service.CategoryService;
import ru.practicum.category.service.PublicCategoryService;
import ru.practicum.dto.category.CategoryDtoResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nikolay Radzivon
 * @Date 22.06.2024
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PublicCategoryServiceImpl implements PublicCategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryChecker;
    private final CategoryMapper categoryMapper;


    /**
     * @param from
     * @param size
     * @return
     */
    @Override
    public List<CategoryDtoResponse> getCategories(int from, int size) {
        var page = PageRequest.of(from / size, size);
        log.info("Запрошен список всех категорий с параметрами запроса from={} size={}", from, size);

        return categoryRepository.findAll(page)
                .stream()
                .map(categoryMapper::toCategoryDtoResponse)
                .collect(Collectors.toList());
    }

    /**
     * @param catId
     * @return
     */
    @Override
    public CategoryDtoResponse getCategory(Long catId) {
        log.info("Запрошена категория с id {}", catId);
        var category = categoryChecker.checkCategory(catId);

        return categoryMapper.toCategoryDtoResponse(category);
    }
}
