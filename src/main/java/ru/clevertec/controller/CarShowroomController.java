package ru.clevertec.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.dto.CarShowroomDto;
import ru.clevertec.service.CarShowroomService;

import java.util.List;

@RestController
@RequestMapping("/api/showrooms")
@RequiredArgsConstructor
@Validated
public class CarShowroomController {
    private final CarShowroomService carShowroomService;

    @PostMapping
    public ResponseEntity<CarShowroomDto> createCarShowroom(@RequestBody @Valid CarShowroomDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carShowroomService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarShowroomDto> findCarShowroomById(@PathVariable Long id) {
        return ResponseEntity.ok(carShowroomService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<CarShowroomDto>> findAllCarShowrooms() {
        return ResponseEntity.ok(carShowroomService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarShowroomDto> updateCarShowroom(@PathVariable Long id,
                                                            @RequestBody @Valid CarShowroomDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carShowroomService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCarShowroomById(@PathVariable Long id) {
        carShowroomService.deleteById(id);
    }
}
