package ru.clevertec.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto {

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @NotNull(message = "Registration date cannot be null")
    private LocalDate registrationDate;

    @NotNull(message = "Contacts cannot be null")
    @Valid
    private Map<@NotNull(message = "Contact type cannot be null")
        @Size(min = 1, max = 50, message = "Contact type must be between 1 and 50 characters") String,
                @NotNull(message = "Contact value cannot be null")
                @Size(min = 1, max = 100, message = "Contact value must be between 1 and 100 characters") String> contacts;
}
