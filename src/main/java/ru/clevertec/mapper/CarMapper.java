package ru.clevertec.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.clevertec.dto.CarDto;
import ru.clevertec.entity.Car;

@Mapper(componentModel = "spring")
public interface CarMapper {

    @Mapping(target = "categoryId", source = "category.id")
    CarDto toCarDto(Car car);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "showroom", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    Car toCar(CarDto carDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "showroom", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    void updateCarFromDto(CarDto carDto, @MappingTarget Car car);
}


