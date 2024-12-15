package ru.clevertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.clevertec.entity.Car;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c FROM Car c WHERE " +
            "(:brand IS NULL OR c.brand = :brand) AND " +
            "(:year IS NULL OR c.year = :year) AND " +
            "(:category IS NULL OR c.category.name = :category) AND " +
            "(:minPrice IS NULL OR c.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR c.price <= :maxPrice)")
    List<Car> findByFilters(@Param("brand") String brand,
                            @Param("year") Integer year,
                            @Param("category") String category,
                            @Param("minPrice") Double minPrice,
                            @Param("maxPrice") Double maxPrice);
}
