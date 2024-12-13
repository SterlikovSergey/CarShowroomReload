package ru.clevertec.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.dto.CarDto;
import ru.clevertec.entity.Car;
import ru.clevertec.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Application is working");
    }

    @PostMapping
    public ResponseEntity<Car> addCar(@Valid @RequestBody CarDto carDto) {
        carService.addCar(carDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @Valid @RequestBody CarDto carDto) {
        carService.updateCar(id, carDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Car>> getCars(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long year,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        List<Car> cars = carService.findCarsByFilters(brand, categoryId, year, minPrice, maxPrice);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Car>> getCarsSortedByPrice(
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        List<Car> cars = carService.findCarsSortedByPrice(ascending);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/page")
    public ResponseEntity<List<Car>> getCarsWithPagination(
            @RequestParam int pageNumber,
            @RequestParam int pageSize
    ) {
        List<Car> cars = carService.findCarsWithPagination(pageNumber, pageSize);
        return ResponseEntity.ok(cars);
    }
}
