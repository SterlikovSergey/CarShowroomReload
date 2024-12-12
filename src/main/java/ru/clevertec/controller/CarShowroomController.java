package ru.clevertec.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.dto.CarShowroomDto;
import ru.clevertec.entity.CarShowroom;
import ru.clevertec.service.CarShowroomService;

@RestController
@RequestMapping("/api/showrooms")
@RequiredArgsConstructor
public class CarShowroomController {

    private final CarShowroomService showroomService;

    @PostMapping
    public ResponseEntity<CarShowroom> addShowroom(@Valid @RequestBody CarShowroomDto showroomDto) {
        CarShowroom savedShowroom = showroomService.addShowroom(showroomDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedShowroom);
    }
}
