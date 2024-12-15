package ru.clevertec.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import ru.clevertec.dto.ReviewDto;
import ru.clevertec.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDto> create(@RequestBody @Valid ReviewDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> findById(@PathVariable @Valid @NotBlank Long id) {
        return ResponseEntity.ok(reviewService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> findAll() {
        return ResponseEntity.ok(reviewService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDto> update(@PathVariable @Valid @NotBlank Long id,
                                            @RequestBody @Valid ReviewDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable @Valid @NotBlank Long id) {
        reviewService.deleteById(id);
    }

    @GetMapping("/search/rating")
    public ResponseEntity<List<ReviewDto>> searchByRating(@RequestParam int rating) {
        return ResponseEntity.ok(reviewService.searchByRating(rating));
    }
}