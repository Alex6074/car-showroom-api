package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.dto.CarShowroomDto;
import ru.clevertec.entity.CarShowroom;
import ru.clevertec.exception.CarShowroomNotFoundException;
import ru.clevertec.mapper.CarShowroomMapper;
import ru.clevertec.repository.CarShowroomRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarShowroomService {
    private final CarShowroomRepository showroomRepository;
    private final CarShowroomMapper showroomMapper;

    @Transactional
    public CarShowroomDto create(CarShowroomDto dto) {
        CarShowroom entity = showroomMapper.toCarShowroom(dto);
        return showroomMapper.toCarShowroomDto(showroomRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public CarShowroomDto findById(Long id) {
        return showroomRepository.findById(id)
                .map(showroomMapper::toCarShowroomDto)
                .orElseThrow(() -> CarShowroomNotFoundException.byCarShowroomId(id));
    }

    @Transactional(readOnly = true)
    public List<CarShowroomDto> findAll() {
        return showroomMapper.toCarShowroomDtoList(showroomRepository.findAll());
    }

    @Transactional
    public CarShowroomDto update(Long id, CarShowroomDto dto) {
        CarShowroom showroom = showroomRepository.findById(id)
                .orElseThrow(() -> CarShowroomNotFoundException.byCarShowroomId(id));

        showroom.setName(dto.name());
        showroom.setAddress(dto.address());

        return showroomMapper.toCarShowroomDto(showroomRepository.save(showroom));
    }

    @Transactional
    public void deleteById(Long id) {
        showroomRepository.deleteById(id);
    }
}
