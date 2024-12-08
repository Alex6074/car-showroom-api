package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.dto.CategoryDto;
import ru.clevertec.entity.Category;
import ru.clevertec.exception.CategoryNotFoundException;
import ru.clevertec.mapper.CategoryMapper;
import ru.clevertec.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = categoryRepository.save(categoryMapper.toCategory(categoryDto));
        return categoryMapper.toCategoryDto(category);
    }

    @Transactional(readOnly = true)
    public CategoryDto findById(Long id) {
        return categoryMapper.toCategoryDto(categoryRepository.findById(id)
                .orElseThrow(() -> CategoryNotFoundException.byCategoryId(id)));
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {
        return categoryMapper.toCategoryDtoList(categoryRepository.findAll());
    }

    @Transactional
    public CategoryDto update(Long categoryId, CategoryDto dto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> CategoryNotFoundException.byCategoryId(categoryId));

        category.setName(dto.name());

        return categoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Transactional
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
