package ru.practicum.repository.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Nikolay Radzivon
 * @Date 04.07.2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountHitProjection {
    private Long count;

    private String app;

    private String uri;
}
