package ru.clevertec.dto;

import jakarta.validation.constraints.DecimalMin;
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
public class CarDto {

    @NotNull(message = "Model cannot be null")
    @Size(min = 1, max = 50, message = "Model must be between 1 and 50 characters")
    private String model;

    @NotNull(message = "Brand cannot be null")
    @Size(min = 1, max = 50, message = "Brand must be between 1 and 50 characters")
    private String brand;

    @NotNull(message = "Year cannot be null")
    @Min(value = 1900, message = "Year must be greater than 1900")
    @Max(value = 2100, message = "Year must be less than 2100")
    private Integer year;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;
}

