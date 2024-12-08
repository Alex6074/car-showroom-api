package ru.clevertec.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.dto.CategoryDto;
import ru.clevertec.entity.Category;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    List<CategoryDto> toCategoryDtoList(List<Category> categories);
    Category toCategory(CategoryDto categoryDto);
    CategoryDto toCategoryDto(Category category);
}
