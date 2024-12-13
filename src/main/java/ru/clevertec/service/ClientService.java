package ru.clevertec.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.dto.ClientDto;
import ru.clevertec.entity.Car;
import ru.clevertec.entity.Client;
import ru.clevertec.exception.CarNotFoundException;
import ru.clevertec.exception.ClientNotFoundException;
import ru.clevertec.mapper.ClientMapper;
import ru.clevertec.repository.CarRepository;
import ru.clevertec.repository.ClientRepository;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final CarRepository carRepository;
    private final ClientMapper clientMapper;

    public Client addClient(ClientDto clientDto) {
        return clientRepository.save(clientMapper.toClient(clientDto));
    }

    public void updateClient(Long id, ClientDto clientDto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client with ID " + id + " not found"));
        clientMapper.updateClientFromDto(clientDto, client);
        clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client with ID " + id + " not found"));
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public void buyCar(Long clientId, Long carId) {
        Client client = getClientById(clientId);
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + id + " not found"));
        client.getCars().add(car);
        clientRepository.save(client);
    }
}
