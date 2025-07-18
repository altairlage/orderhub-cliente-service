package com.fiap.orderhubClienteService.mapper;

import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.dto.clientes.CriarClienteDTO;
import com.fiap.orderhubClienteService.dtos.ClienteApiRequestDto;
import com.fiap.orderhubClienteService.dtos.ClienteApiResponseDto;

public class ClienteApiDtoMapper {
    public static ClienteApiResponseDto clienteDtoToResponseDto(ClienteDTO clienteDTO) {
        return new ClienteApiResponseDto(
                clienteDTO.id(),
                clienteDTO.nome(),
                clienteDTO.cpf(),
                clienteDTO.dataNascimento(),
                clienteDTO.endereco(),
                clienteDTO.numeroContato(),
                clienteDTO.email(),
                clienteDTO.infoPagamento()
        );
    }

    public static CriarClienteDTO requestDtoToCriarClienteDto(ClienteApiRequestDto requestDTO) {
        return new CriarClienteDTO(
                requestDTO.nome(),
                requestDTO.cpf(),
                requestDTO.dataNascimento(),
                requestDTO.endereco(),
                requestDTO.numeroContato(),
                requestDTO.email(),
                requestDTO.infoPagamento()
        );
    }

    public static ClienteDTO requestDtoToClienteDto(Long id, ClienteApiRequestDto requestDto) {
        return new ClienteDTO(
                id,
                requestDto.nome(),
                requestDto.cpf(),
                requestDto.dataNascimento(),
                requestDto.endereco(),
                requestDto.numeroContato(),
                requestDto.email(),
                requestDto.infoPagamento()
        );
    }
}
