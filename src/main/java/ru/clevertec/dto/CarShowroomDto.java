package ru.clevertec.dto;

import jakarta.validation.constraints.NotBlank;

public record CarShowroomDto (
        Long id,
        @NotBlank String name,
        @NotBlank String address) {
}
