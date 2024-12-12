package ru.clevertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.entity.Car;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findCarsByFilters(String brand, Long year, Long categoryId, Double minPrice, Double maxPrice);

    List<Car> findCarsSortedByPrice(boolean ascending);

    List<Car> findCarsWithPagination(int pageNumber, int pageSize);
}
