package ru.practicum.converter;

import org.springframework.core.convert.converter.Converter;
import ru.practicum.exception.StringToEventSortTypeConverterException;
import ru.practicum.location.dto.EventSortType;

/**
 * @author Nikolay Radzivon
 * @Date 10.07.2024
 */
public class StringToEventSortTypeConverter implements Converter<String, EventSortType> {

    @Override
    public EventSortType convert(String source) {
        try {
            return EventSortType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new StringToEventSortTypeConverterException("Неверное значение для типа сортировки событий: " + source);
        }
    }
}
