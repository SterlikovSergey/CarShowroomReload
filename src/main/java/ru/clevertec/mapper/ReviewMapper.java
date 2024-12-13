package ru.clevertec.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.clevertec.dto.ReviewDto;
import ru.clevertec.entity.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "carId", source = "car.id")
    ReviewDto toReviewDto(Review review);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "car", ignore = true)
    Review toReview(ReviewDto reviewDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "car", ignore = true)
    void updateReviewFromDto(ReviewDto reviewDto, @MappingTarget Review review);
}


