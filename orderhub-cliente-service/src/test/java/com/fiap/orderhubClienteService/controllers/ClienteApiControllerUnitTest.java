package com.fiap.orderhubClienteService.controllers;

import br.com.orderhub.core.controller.ClienteController;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.exceptions.ClienteNaoEncontradoException;
import com.fiap.orderhubClienteService.dtos.ClienteApiRequestDto;
import com.fiap.orderhubClienteService.dtos.ClienteApiResponseDto;
import com.fiap.orderhubClienteService.utils.ClienteServiceUtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteApiControllerUnitTest {

    @Mock
    private ClienteController clienteController;

    @InjectMocks
    private ClienteApiController clienteApiController;

    ClienteDTO clienteDTO;
    ClienteApiRequestDto requestDto;
    ClienteApiResponseDto responseDto;

    @BeforeEach
    void setup() {
        clienteDTO = ClienteServiceUtilsTest.criaClienteDto();
        requestDto = ClienteServiceUtilsTest.criaClienteApiRequestDto();
        responseDto = ClienteServiceUtilsTest.criaClienteApiResponseDto();
    }

    @Test
    void deveBuscarProdutoPorIdComSucesso() {
        Long id = 1L;

        when(clienteController.buscarClientePorId(id)).thenReturn(clienteDTO);

        ResponseEntity<ClienteApiResponseDto> response = clienteApiController.buscarClientePorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void deveRetornarNotFoundAoBuscarClienteInexistente() {
        Long id = 2L;
        when(clienteController.buscarClientePorId(id)).thenThrow(new ClienteNaoEncontradoException("Cliente não encontrado"));

        assertThrows(ClienteNaoEncontradoException.class, () -> clienteApiController.buscarClientePorId(id));

    }

    @Test
    void deveBuscarClientePorNome() {
        String nome = "João";
        when(clienteController.buscarClientePorNome(nome)).thenReturn(clienteDTO);

        ResponseEntity<ClienteApiResponseDto> response = clienteApiController.buscarClientePorNome(nome);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void deveRetornarNotFoundAoBuscarNomeInexistente() {
        String nome = "NomeInexistente";
        when(clienteController.buscarClientePorNome(nome)).thenThrow(new ClienteNaoEncontradoException("Cliente não encontrado"));

        assertThrows(ClienteNaoEncontradoException.class, () -> clienteApiController.buscarClientePorNome(nome));
    }

    @Test
    void deveBuscarClientePorCpf() {
        String cpf = "123.456.789-00";
        when(clienteController.buscarClientePorCpf(cpf)).thenReturn(clienteDTO);

        ResponseEntity<ClienteApiResponseDto> response = clienteApiController.buscarClientePorCpf(cpf);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void deveRetornarNotFoundAoBuscarCpfInexistente() {
        String cpf = "000.000.000-00";
        when(clienteController.buscarClientePorCpf(cpf)).thenThrow(new ClienteNaoEncontradoException("Cliente não encontrado"));

        assertThrows(ClienteNaoEncontradoException.class, () -> clienteApiController.buscarClientePorCpf(cpf));
    }

    @Test
    void listarTodosClientes() {
        List<ClienteDTO> clientes = List.of(clienteDTO);
        when(clienteController.listarTodosClientes()).thenReturn(clientes);

        ResponseEntity<List<ClienteApiResponseDto>> response = clienteApiController.listarTodosClientes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void criarCliente() {
        when(clienteController.criarCliente(any())).thenReturn(clienteDTO);

        ResponseEntity<ClienteApiResponseDto> response = clienteApiController.criarCliente(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void editarCliente() {
        Long id = 1L;
        when(clienteController.atualizarCliente(any())).thenReturn(clienteDTO);

        ResponseEntity<ClienteApiResponseDto> response = clienteApiController.editarCliente(id, requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void deletarCliente() {
        Long id = 1L;
        doNothing().when(clienteController).removerCliente(id);

        ResponseEntity<Void> response = clienteApiController.deletarCliente(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}