package ru.practicum.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.category.model.Category;

/**
 * @author Nikolay Radzivon
 * @Date 25.06.2024
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
