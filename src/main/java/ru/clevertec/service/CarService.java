package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.dto.CarDto;
import ru.clevertec.entity.Car;
import ru.clevertec.entity.CarShowroom;
import ru.clevertec.exception.CarNotFoundException;
import ru.clevertec.exception.CarShowroomNotFoundException;
import ru.clevertec.mapper.CarMapper;
import ru.clevertec.mapper.CarShowroomMapper;
import ru.clevertec.mapper.CategoryMapper;
import ru.clevertec.annotation.MonitorPerformance;
import ru.clevertec.repository.CarRepository;
import ru.clevertec.repository.CarShowroomRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final CarShowroomRepository showroomRepository;
    private final CarMapper carMapper;
    private final CarShowroomMapper carShowroomMapper;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CarDto create(CarDto carDto) {
        Car car = carRepository.save(carMapper.toCar(carDto));
        return carMapper.toCarDto(car);
    }

    @MonitorPerformance
    @Transactional(readOnly = true)
    public CarDto findById(Long id) {
        return carMapper.toCarDto(carRepository.findById(id)
                .orElseThrow(() -> CarNotFoundException.byCarId(id)));
    }

    @MonitorPerformance
    @Transactional(readOnly = true)
    public List<CarDto> findAll() {
        return carMapper.toCarDtoList(carRepository.findAll());
    }

    @Transactional
    public CarDto update(Long carId, CarDto carDto) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> CarNotFoundException.byCarId(carId));

        car.setShowroom(carShowroomMapper.toCarShowroom(carDto.showroom()));
        car.setBrand(carDto.brand());
        car.setModel(carDto.model());
        car.setYear(carDto.year());
        car.setCategory(categoryMapper.toCategory(carDto.category()));
        car.setPrice(carDto.price());

        return carMapper.toCarDto(carRepository.save(car));
    }

    @Transactional
    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

    @Transactional
    public void assignCarToShowroom(Long carId, Long showroomId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> CarNotFoundException.byCarId(carId));
        CarShowroom showroom = showroomRepository.findById(showroomId)
                .orElseThrow(() -> CarShowroomNotFoundException.byCarShowroomId(showroomId));
        car.setShowroom(showroom);
        carRepository.save(car);
    }

    @MonitorPerformance
    @Transactional(readOnly = true)
    public List<CarDto> findCarsByParameters(String brand, Integer year, String category, Double minPrice, Double maxPrice) {
        return carMapper.toCarDtoList(carRepository.findByFilters(brand, year, category, minPrice, maxPrice));
    }

    @Transactional(readOnly = true)
    public List<Car> findCarsSortedByPrice(boolean ascending) {
        Sort sort = ascending ? Sort.by("price").ascending() : Sort.by("price").descending();
        return carRepository.findAll(sort);
    }

    @Transactional(readOnly = true)
    public List<Car> findCarsWithPagination(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return carRepository.findAll(pageable).getContent();
    }
}
