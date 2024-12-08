package ru.clevertec.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.List;

public record ClientDto(
         Long id,
        @NotBlank String name,
        @NotEmpty List<@NotBlank String> contacts,
        @NotNull @PastOrPresent @JsonFormat(pattern = "dd-MM-yyyy") LocalDate registrationDate) {
}
