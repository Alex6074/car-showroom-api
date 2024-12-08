package ru.clevertec.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
