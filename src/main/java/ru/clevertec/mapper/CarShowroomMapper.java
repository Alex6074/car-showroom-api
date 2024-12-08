package ru.clevertec.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.dto.CarShowroomDto;
import ru.clevertec.entity.CarShowroom;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarShowroomMapper {
    CarShowroomDto toCarShowroomDto(CarShowroom entity);
    CarShowroom toCarShowroom(CarShowroomDto dto);
    List<CarShowroomDto> toCarShowroomDtoList(List<CarShowroom> entities);
}
