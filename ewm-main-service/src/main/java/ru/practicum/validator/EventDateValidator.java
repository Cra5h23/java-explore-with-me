package ru.practicum.validator;

import ru.practicum.exception.EventDateValidateException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

/**
 * @author Nikolay Radzivon
 * @Date 20.06.2024
 */
public class EventDateValidator implements ConstraintValidator<EventDate, LocalDateTime> {
    private LocalDateTime data;
    private long time;

    @Override
    public void initialize(EventDate constraintAnnotation) {
        time = constraintAnnotation.value();
        data = LocalDateTime.now().plusHours(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (value.isBefore(data)) {
            throw new EventDateValidateException(String.format(
                    "Дата и время начала события не должно быть раньше чем за %d часа от текущего времени", time));
        }
        return true;
    }
}
