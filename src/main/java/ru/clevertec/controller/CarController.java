package ru.clevertec.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.dto.CarDto;
import ru.clevertec.service.CarService;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/{carId}")
    public ResponseEntity<CarDto> findCarById(@PathVariable("carId") @Valid @NotBlank Long carId) {
        CarDto car = carService.findById(carId);
        return ResponseEntity.ok()
                .body(car);
    }

    @PostMapping
    public ResponseEntity<CarDto> createCar(@RequestBody @Valid CarDto carDto) {
        CarDto createdCar = carService.create(carDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdCar);
    }

    @PutMapping("/{carId}")
    public ResponseEntity<CarDto> updateCar(@PathVariable("carId") @Valid @NotBlank Long carId,
                                            @RequestBody @Valid CarDto carDto) {
        CarDto newCar = carService.update(carId, carDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newCar);
    }

    @DeleteMapping("/{carId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable("carId") @Valid @NotBlank Long carId) {
        carService.deleteById(carId);
    }

    @GetMapping
    public ResponseEntity<List<CarDto>> findCarsByFilters(@RequestParam(required = false) String brand,
                                                          @RequestParam(required = false) String category,
                                                          @RequestParam(required = false) Integer year,
                                                          @RequestParam(required = false) Double minPrice,
                                                          @RequestParam(required = false) Double maxPrice) {
        return ResponseEntity.ok()
                .body(carService.findCarsByParameters(brand, year, category, minPrice, maxPrice));
    }

    @PostMapping("/{carId}/showrooms/{showroomId}")
    public ResponseEntity<Void> assignCarToShowroom(@PathVariable @Valid @NotBlank Long carId,
                                                    @PathVariable @Valid @NotBlank Long showroomId) {
        carService.assignCarToShowroom(carId, showroomId);
        return ResponseEntity.noContent().build();
    }
}
