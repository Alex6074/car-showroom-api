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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.dto.ClientDto;
import ru.clevertec.dto.LeaveReviewForCarDto;
import ru.clevertec.dto.ReviewDto;
import ru.clevertec.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Validated
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDto> createClient(@RequestBody @Valid ClientDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> findClientById(@PathVariable @Valid @NotBlank Long id) {
        return ResponseEntity.ok(clientService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> findAllClients() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable @Valid @NotBlank Long id,
                                                  @RequestBody @Valid ClientDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClientById(@PathVariable @Valid @NotBlank Long id) {
        clientService.deleteById(id);
    }

    @PostMapping("/{clientId}/buy-car/{carId}")
    public ResponseEntity<ClientDto> buyCar(@PathVariable @Valid @NotBlank Long clientId,
                                            @PathVariable @Valid @NotBlank Long carId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientService.buyCar(clientId, carId));
    }

    @PostMapping("/{clientId}/leave-review/{carId}")
    public ResponseEntity<ReviewDto> leaveReview(@PathVariable @Valid @NotBlank Long clientId,
                                                 @PathVariable @Valid @NotBlank Long carId,
                                                 @RequestBody @Valid LeaveReviewForCarDto reviewDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientService.leaveReviewForCar(clientId, carId, reviewDto));
    }
}