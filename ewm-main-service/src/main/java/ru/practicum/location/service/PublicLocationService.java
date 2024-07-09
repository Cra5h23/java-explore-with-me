package ru.practicum.location.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.location.dto.LocationDtoResponse;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 07.07.2024
 */
public interface PublicLocationService {
    List<LocationDtoResponse> getLocations(GetLocationsParams params);

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class GetLocationsParams {
        @PositiveOrZero(message = "Параметр from должен равен 0 или быть больше")
        private int from = 0;

        @Min(value = 1, message = "Параметр size не может быть меньше {min}")
        @Max(value = 100,message = "Параметр size не может быть больше {max}")
        private int size = 10;
    }
}
