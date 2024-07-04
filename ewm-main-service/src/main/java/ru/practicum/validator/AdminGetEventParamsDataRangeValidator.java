package ru.practicum.validator;

import ru.practicum.event.service.AdminEventService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Nikolay Radzivon
 * @Date 04.07.2024
 */
public class AdminGetEventParamsDataRangeValidator implements ConstraintValidator<ValidDateRange, AdminEventService.GetEventsParams> {

    @Override
    public boolean isValid(AdminEventService.GetEventsParams eventsParams, ConstraintValidatorContext context) {
        if (eventsParams != null) {
            var rangeStart = eventsParams.getRangeStart();
            var rangeEnd = eventsParams.getRangeEnd();
            if (rangeStart == null || rangeEnd == null) {
                return true;
            }
            return rangeEnd.isAfter(rangeStart);
        }
        return true;
    }
}
