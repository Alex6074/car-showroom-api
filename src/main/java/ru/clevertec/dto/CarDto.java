package ru.clevertec.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.Year;

public record CarDto(
        Long id,
        @NotBlank String model,
        @NotBlank String brand,
        @Min(value = 1886) @Max(value = Year.MAX_VALUE) int year,
        @Positive BigDecimal price,
        @NotNull CategoryDto category,
        @NotNull CarShowroomDto showroom) {
}
