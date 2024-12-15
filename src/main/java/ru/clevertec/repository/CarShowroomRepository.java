package ru.clevertec.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.entity.CarShowroom;

import java.util.List;

public interface CarShowroomRepository extends JpaRepository<CarShowroom, Long> {
    @EntityGraph(value = "CarShowroom.withCarsAndCategories", type = EntityGraph.EntityGraphType.FETCH)
    List<CarShowroom> findAll();
}
