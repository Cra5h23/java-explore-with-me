package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Hit;

/**
 * @author Nikolay Radzivon
 * @Date 14.06.2024
 */
public interface StatsRepository extends JpaRepository<Hit, Long>, CustomStatsRepository {
}

