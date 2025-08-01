package com.fiap.orderhubClienteService.utils;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import com.fiap.orderhubClienteService.dtos.ClienteApiRequestDto;
import com.fiap.orderhubClienteService.dtos.ClienteApiResponseDto;
import com.fiap.orderhubClienteService.mapper.ClienteApiDtoMapper;
import com.fiap.orderhubClienteService.persistence.ClienteEntity;

public abstract class ClienteServiceUtilsTest {

    public static ClienteApiResponseDto criaClienteApiResponseDto() {
        ClienteDTO clienteDto = new ClienteDTO(
                1L,
                "Jorge",
                "123.456.789-09",
                "07/12/2015",
                "Rua Teste, 123, Bairro XYZ",
                "(99) 99999-9999",
                "email@email.com",
                "Cartao de Credito"
        );

        return ClienteApiDtoMapper.clienteDtoToResponseDto(clienteDto);
    }

    public static ClienteApiRequestDto criaClienteApiRequestDto() {
        ClienteApiRequestDto requestDto = new ClienteApiRequestDto(
                "Jorge",
                "123.456.789-09",
                "07/12/2015",
                "Rua Teste, 123, Bairro XYZ",
                "(99) 99999-9999",
                "email@email.com",
                "Cartao de Credito"
        );

        return requestDto;
    }

    public static ClienteEntity criaClienteEntity() {
        return new ClienteEntity(
                1L,
                "Jorge",
                "123.456.789-09",
                "07/12/2015",
                "Rua Teste, 123, Bairro XYZ",
                "(99) 99999-9999",
                "email@email.com",
                "Cartao de Credito"
        );
    }

    public static Cliente criaCliente() {
        return new Cliente(
                1L,
                "Jorge",
                "123.456.789-09",
                "07/12/2015",
                "Rua Teste, 123, Bairro XYZ",
                "(99) 99999-9999",
                "email@email.com",
                "Cartao de Credito"
        );
    }
}
