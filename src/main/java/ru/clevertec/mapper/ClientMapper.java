package ru.clevertec.mapper;


import org.mapstruct.Mapper;
import ru.clevertec.dto.ClientDto;
import ru.clevertec.entity.Client;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDto toClientDto(Client client);
    Client toClient(ClientDto dto);
    List<ClientDto> toClientDtoList(List<Client> clients);
}