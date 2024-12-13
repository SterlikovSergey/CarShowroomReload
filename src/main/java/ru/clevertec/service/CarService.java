package ru.clevertec.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.dto.CarDto;
import ru.clevertec.entity.Car;
import ru.clevertec.entity.CarShowroom;
import ru.clevertec.entity.Category;
import ru.clevertec.exception.CarNotFoundException;
import ru.clevertec.exception.CategoryNotFoundException;
import ru.clevertec.exception.ShowroomNotFoundException;
import ru.clevertec.mapper.CarMapper;
import ru.clevertec.repository.CarRepository;
import ru.clevertec.repository.CarShowroomRepository;
import ru.clevertec.repository.CategoryRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final CarShowroomRepository carShowroomRepository;
    private final CategoryRepository categoryRepository;
    private final CarMapper carMapper;

    public void addCar(CarDto carDto) {
        Car car = carMapper.toCar(carDto);
        car.setCategory(getCategoryById(carDto.getCategoryId()));
        carRepository.save(car);
    }

    public void updateCar(Long id, CarDto carDto) {
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Car with ID " + id + " not found"));
        carMapper.updateCarFromDto(carDto, car);
        car.setCategory(getCategoryById(carDto.getCategoryId()));
        carRepository.save(car);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Car with ID " + id + " not found"));
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public void assignCarToShowroom(Long carId, Long showroomId) {
        Car car = getCarById(carId);
        CarShowroom showroom = getShowroomById(showroomId);
        car.setShowroom(showroom);
        carRepository.save(car);
    }

    public List<Car> findCarsByFilters(String brand, Long year, Long categoryId, Double minPrice, Double maxPrice) {
        return carRepository.findCarsByFilters(brand, year, categoryId, minPrice, maxPrice);
    }

    public List<Car> findCarsSortedByPrice(boolean ascending) {
        return carRepository.findCarsSortedByPrice(ascending);
    }

    public List<Car> findCarsWithPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return carRepository.findAll(pageable).getContent();
    }

    private Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category with ID " + id + " not found"));
    }

    private CarShowroom getShowroomById(Long id) {
        return carShowroomRepository.findById(id).orElseThrow(() -> new ShowroomNotFoundException("Showroom with ID " + id + " not found"));
    }
}
