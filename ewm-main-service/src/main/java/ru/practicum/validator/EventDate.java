package ru.practicum.validator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Nikolay Radzivon
 * @Date 20.06.2024
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EventDateValidator.class)
public @interface EventDate {
    String message() default "Дата и время события не может быть раньше чем через {value} часа от текущего времени";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

    long value() default 2;
}
