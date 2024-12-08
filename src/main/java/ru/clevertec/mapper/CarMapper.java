package ru.clevertec.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.dto.CarDto;
import ru.clevertec.entity.Car;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, CarShowroomMapper.class})
public interface CarMapper {
    List<CarDto> toCarDtoList(List<Car> cars);
    Car toCar(CarDto carDto);
    CarDto toCarDto(Car car);
}
