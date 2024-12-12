package ru.clevertec.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.dto.ClientDto;
import ru.clevertec.entity.Client;
import ru.clevertec.service.ClientService;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<Client> addClient(@Valid @RequestBody ClientDto clientDto) {
        Client savedClient = clientService.addClient(clientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @PostMapping("/{clientId}/buy")
    public ResponseEntity<Void> buyCar(
            @PathVariable Long clientId,
            @RequestParam Long carId
    ) {
        clientService.buyCar(clientId, carId);
        return ResponseEntity.ok().build();
    }
}

