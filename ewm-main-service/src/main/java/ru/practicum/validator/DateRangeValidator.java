package ru.practicum.validator;

import ru.practicum.event.service.PublicEventService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Nikolay Radzivon
 * @Date 02.07.2024
 */
class DateRangeValidator implements ConstraintValidator<ValidDateRange, PublicEventService.GetEventsParams> {

    @Override
    public boolean isValid(PublicEventService.GetEventsParams eventsParams, ConstraintValidatorContext context) {
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
