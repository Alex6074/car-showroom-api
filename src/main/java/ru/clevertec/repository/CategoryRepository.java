package ru.clevertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
