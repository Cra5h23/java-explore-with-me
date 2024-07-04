package ru.practicum.validator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Nikolay Radzivon
 * @Date 02.07.2024
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PublicGetEventParamsDateRangeValidator.class, AdminGetEventParamsDataRangeValidator.class})
public @interface ValidDateRange {
    String message() default "Дата окончания поиска должна быть позже даты начала поиска";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}

