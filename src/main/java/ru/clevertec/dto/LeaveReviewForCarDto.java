package ru.clevertec.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LeaveReviewForCarDto(
        Long id,
        @NotBlank @Size(max = 1000) String text,
        @Min(1) @Max(5) int rating){
}
