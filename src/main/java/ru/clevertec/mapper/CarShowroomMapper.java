package ru.clevertec.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.clevertec.dto.CarShowroomDto;
import ru.clevertec.entity.CarShowroom;

@Mapper(componentModel = "spring")
public interface CarShowroomMapper {

    CarShowroomDto toCarShowroomDto(CarShowroom carShowroom);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cars", ignore = true)
    CarShowroom toCarShowroom(CarShowroomDto carShowroomDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cars", ignore = true)
    void updateShowroomFromDto(CarShowroomDto carShowroomDto, @MappingTarget CarShowroom carShowroom);
}
