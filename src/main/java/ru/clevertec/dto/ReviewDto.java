package ru.clevertec.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {

    @NotNull(message = "Review text cannot be null")
    @Size(min = 1, max = 500, message = "Review text must be between 1 and 500 characters")
    private String text;

    @NotNull(message = "Rating cannot be null")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    @NotNull(message = "Client ID cannot be null")
    private Long clientId;

    @NotNull(message = "Car ID cannot be null")
    private Long carId;
}
