package ru.practicum.event.repository.projection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.category.model.Category;
import ru.practicum.user.model.User;

import java.time.ZonedDateTime;

/**
 * @author Nikolay Radzivon
 * @Date 08.07.2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventShortProjection {
    private long id;
    private String title;
    private String annotation;
    private Category category;
    private ZonedDateTime eventDate;
    private User initiator;
    private boolean paid;
    private ZonedDateTime publishedOn;
    private long confirmedRequests;
}
