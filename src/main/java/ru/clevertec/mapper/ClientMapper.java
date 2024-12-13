package ru.clevertec.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.clevertec.dto.ClientDto;
import ru.clevertec.entity.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDto toClientDto(Client client);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cars", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    Client toClient(ClientDto clientDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cars", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    void updateClientFromDto(ClientDto clientDto, @MappingTarget Client client);
}

