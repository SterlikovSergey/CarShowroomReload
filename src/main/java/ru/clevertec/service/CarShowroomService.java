package ru.clevertec.service;

import ru.clevertec.entity.CarShowroom;
import ru.clevertec.repository.impl.CarShowroomRepository;

import java.util.List;

public class CarShowroomService {

    private final CarShowroomRepository carShowroomRepository = new CarShowroomRepository();

    public void addShowroom(CarShowroom showroom) {
        carShowroomRepository.save(showroom);
    }

    public void updateShowroom(CarShowroom showroom) {
        carShowroomRepository.update(showroom);
    }

    public void deleteShowroom(CarShowroom showroom) {
        carShowroomRepository.delete(showroom);
    }

    public CarShowroom getShowroomById(Long id) {
        return carShowroomRepository.findById(id);
    }

    public List<CarShowroom> getAllShowrooms() {
        return carShowroomRepository.findAll();
    }

    public CarShowroom getShowroomWithCars(Long showroomId) {
        return carShowroomRepository.findShowroomWithCars(showroomId);
    }
}
