package ru.clevertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.entity.CarShowroom;

public interface CarShowroomRepository extends JpaRepository<CarShowroom, Long> {
}
