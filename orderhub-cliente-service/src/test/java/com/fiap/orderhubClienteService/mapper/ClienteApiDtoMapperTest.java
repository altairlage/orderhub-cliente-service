package com.fiap.orderhubClienteService.mapper;

import com.fiap.orderhubClienteService.dtos.ClienteApiRequestDto;
import com.fiap.orderhubClienteService.dtos.ClienteApiResponseDto;
import com.fiap.orderhubClienteService.utils.ClienteServiceUtilsTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteApiDtoMapperTest {

    @Test
    void clienteDtoToResponseDto() {
        ClienteApiResponseDto response = ClienteServiceUtilsTest.criaClienteApiResponseDto();

        assertEquals("Jorge", response.nome());
        assertEquals("123.456.789.10", response.cpf());
        assertEquals("07/12/2015", response.dataNascimento());
        assertEquals("Rua Teste, 123, Bairro XYZ", response.endereco());
        assertEquals("(99) 99999-9999", response.numeroContato());
        assertEquals("email@email.com", response.email());
        assertEquals("Cartao de Credito", response.infoPagamento());
    }

    @Test
    void requestDtoToCriarClienteDto() {
        ClienteApiResponseDto response = ClienteServiceUtilsTest.criaClienteApiResponseDto();

        assertEquals("Jorge", response.nome());
        assertEquals("123.456.789.10", response.cpf());
        assertEquals("07/12/2015", response.dataNascimento());
        assertEquals("Rua Teste, 123, Bairro XYZ", response.endereco());
        assertEquals("(99) 99999-9999", response.numeroContato());
        assertEquals("email@email.com", response.email());
        assertEquals("Cartao de Credito", response.infoPagamento());
    }

    @Test
    void requestDtoToClienteDto() {
        Long id = 1L;
        ClienteApiRequestDto response = ClienteServiceUtilsTest.criaClienteApiRequestDto();

        var dto = ClienteApiDtoMapper.requestDtoToClienteDto(id, response);

        assertNotNull(dto);
        assertEquals("Jorge", dto.nome());
        assertEquals("123.456.789.10", dto.cpf());
        assertEquals("07/12/2015", dto.dataNascimento());
        assertEquals("Rua Teste, 123, Bairro XYZ", dto.endereco());
        assertEquals("(99) 99999-9999", dto.numeroContato());
        assertEquals("email@email.com", dto.email());
        assertEquals("Cartao de Credito", dto.infoPagamento());
    }
}