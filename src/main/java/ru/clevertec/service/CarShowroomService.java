package ru.clevertec.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.dto.CarShowroomDto;
import ru.clevertec.entity.CarShowroom;
import ru.clevertec.exception.ShowroomNotFoundException;
import ru.clevertec.mapper.CarShowroomMapper;
import ru.clevertec.repository.CarShowroomRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CarShowroomService {

    private final CarShowroomRepository carShowroomRepository;
    private final CarShowroomMapper carShowroomMapper;

    public CarShowroom addShowroom(CarShowroom showroom) {
        return carShowroomRepository.save(showroom);
    }

    public CarShowroom addShowroom(CarShowroomDto carShowroomDto) {
        CarShowroom carShowroom = carShowroomMapper.toCarShowroom(carShowroomDto);
        return carShowroomRepository.save(carShowroom);
    }

    public void updateShowroom(Long id, CarShowroomDto carShowroomDto) {
        CarShowroom carShowroom = carShowroomRepository.findById(id)
                .orElseThrow(() -> new ShowroomNotFoundException("Showroom with ID " + id + " not found"));
        carShowroomMapper.updateShowroomFromDto(carShowroomDto, carShowroom);
        carShowroomRepository.save(carShowroom);
    }

    public void updateShowroom(Long id, CarShowroom updatedShowroom) {
        CarShowroom showroom = carShowroomRepository.findById(id)
                .orElseThrow(() -> new ShowroomNotFoundException("Showroom with ID " + id + " not found"));
        showroom.setName(updatedShowroom.getName());
        showroom.setAddress(updatedShowroom.getAddress());
        carShowroomRepository.save(showroom);
    }

    public void deleteShowroom(Long id) {
        carShowroomRepository.deleteById(id);
    }

    public CarShowroom getShowroomById(Long id) {
        return carShowroomRepository.findById(id)
                .orElseThrow(() -> new ShowroomNotFoundException("Showroom with ID " + id + " not found"));
    }

    public List<CarShowroom> getAllShowrooms() {
        return carShowroomRepository.findAll();
    }
}
