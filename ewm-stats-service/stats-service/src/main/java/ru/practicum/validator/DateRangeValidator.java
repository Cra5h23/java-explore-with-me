package ru.practicum.validator;

import ru.practicum.service.StatsService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Nikolay Radzivon
 * @Date 02.07.2024
 */
class DateRangeValidator implements ConstraintValidator<ValidDateRange, StatsService.Params> {

    @Override
    public boolean isValid(StatsService.Params params, ConstraintValidatorContext context) {
        if (params != null) {
            var rangeStart = params.getStart();
            var rangeEnd = params.getEnd();
            if (rangeStart == null || rangeEnd == null || rangeStart.equals(rangeEnd)) {
                return true;
            }
            return rangeEnd.isAfter(rangeStart);
        }
        return true;
    }
}
