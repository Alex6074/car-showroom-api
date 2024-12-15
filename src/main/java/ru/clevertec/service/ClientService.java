package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.dto.ClientDto;
import ru.clevertec.dto.LeaveReviewForCarDto;
import ru.clevertec.dto.ReviewDto;
import ru.clevertec.entity.Car;
import ru.clevertec.entity.Client;
import ru.clevertec.entity.Review;
import ru.clevertec.exception.CarNotFoundException;
import ru.clevertec.exception.ClientNotFoundException;
import ru.clevertec.mapper.ClientMapper;
import ru.clevertec.mapper.ReviewMapper;
import ru.clevertec.repository.CarRepository;
import ru.clevertec.repository.ClientRepository;
import ru.clevertec.repository.ReviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final CarRepository carRepository;
    private final ClientMapper clientMapper;
    private final ReviewMapper reviewMapper;
    private final ReviewRepository reviewRepository;

    @Transactional
    public ClientDto create(ClientDto dto) {
        Client client = clientMapper.toClient(dto);
        return clientMapper.toClientDto(clientRepository.save(client));
    }

    @Transactional(readOnly = true)
    public ClientDto findById(Long id) {
        return clientRepository.findById(id)
                .map(clientMapper::toClientDto)
                .orElseThrow(() -> ClientNotFoundException.byClientId(id));
    }

    @Transactional(readOnly = true)
    public List<ClientDto> findAll() {
        return clientMapper.toClientDtoList(clientRepository.findAll());
    }

    @Transactional
    public ClientDto update(Long id, ClientDto dto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> ClientNotFoundException.byClientId(id));

        client.setName(dto.name());
        client.setContacts(dto.contacts());
        client.setRegistrationDate(dto.registrationDate());

        return clientMapper.toClientDto(clientRepository.save(client));
    }

    @Transactional
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    @Transactional
    public ClientDto buyCar(Long clientId, Long carId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> ClientNotFoundException.byClientId(clientId));
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> CarNotFoundException.byCarId(carId));
        client.getCars().add(car);
        return clientMapper.toClientDto(clientRepository.save(client));
    }

    @Transactional
    public ReviewDto leaveReviewForCar(Long clientId, Long carId, LeaveReviewForCarDto reviewDto) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> CarNotFoundException.byCarId(carId));
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> ClientNotFoundException.byClientId(clientId));

        Review review = new Review();
        review.setText(reviewDto.text());
        review.setRating(reviewDto.rating());
        review.setClient(client);
        review.setCar(car);

        return reviewMapper.toReviewDto(reviewRepository.save(review));
    }
}
