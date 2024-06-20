package ru.practicum.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

/**
 * @author Nikolay Radzivon
 * @Date 20.06.2024
 */
public class EventDateValidator implements ConstraintValidator<EventDate, LocalDateTime> {
    private LocalDateTime data;

    @Override
    public void initialize(EventDate constraintAnnotation) {
        data = LocalDateTime.now().plusHours(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        return value.isBefore(data);
    }
}
