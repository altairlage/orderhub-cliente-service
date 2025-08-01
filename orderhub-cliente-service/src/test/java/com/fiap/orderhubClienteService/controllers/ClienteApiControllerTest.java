package com.fiap.orderhubClienteService.controllers;

import br.com.orderhub.core.controller.ClienteController;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.dto.clientes.CriarClienteDTO;
import br.com.orderhub.core.exceptions.ClienteNaoEncontradoException;
import br.com.orderhub.core.exceptions.CpfJaCadastradoException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.orderhubClienteService.dtos.ClienteApiRequestDto;
import com.fiap.orderhubClienteService.dtos.ClienteApiResponseDto;
import com.fiap.orderhubClienteService.handlers.ClientesExceptionHandler;
import com.fiap.orderhubClienteService.utils.ClienteServiceUtilsTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ClienteApiControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ClienteController clienteController;

    @InjectMocks
    private ClienteApiController clienteApiController;

    private MockMvc mockMvc;
    private AutoCloseable mocks;

    private ClienteDTO clienteDTO;
    private ClienteApiRequestDto requestDto;
    private ClienteApiResponseDto responseDto;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);

        clienteApiController = new ClienteApiController(clienteController);

        mockMvc = MockMvcBuilders.standaloneSetup(clienteApiController)
                .setControllerAdvice(new ClientesExceptionHandler())
                .addFilter((req, res, chain) -> {
                    res.setCharacterEncoding("UTF-8");
                    chain.doFilter(req, res);
                })
                .build();

        clienteDTO = ClienteServiceUtilsTest.criaClienteDto();
        responseDto = ClienteServiceUtilsTest.criaClienteApiResponseDto();
        requestDto = ClienteServiceUtilsTest.criaClienteApiRequestDto();
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Nested
    class GetClientesCases {
        @Test
        void deveBuscarProdutoPorIdComSucesso() throws Exception {
            Long id = 1L;

            when(clienteController.buscarClientePorId(id)).thenReturn(clienteDTO);

            mockMvc.perform(get("/clientes/id/{id}", id)
                            .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(id))
                    .andExpect(jsonPath("$.nome").value("Jorge"))
                    .andExpect(jsonPath("$.cpf").value("123.456.789-09"))
                    .andExpect(jsonPath("$.dataNascimento").value("07/12/2015"))
                    .andExpect(jsonPath("$.endereco").value("Rua Teste, 123, Bairro XYZ"))
                    .andExpect(jsonPath("$.numeroContato").value("(99) 99999-9999"))
                    .andExpect(jsonPath("$.email").value("email@email.com"))
                    .andExpect(jsonPath("$.infoPagamento").value("Cartao de Credito"));
        }

        @Test
        void deveRetornarNotFoundAoBuscarClienteInexistente() throws Exception {
            Long id = 2L;
            doThrow(new ClienteNaoEncontradoException("Cliente com id " + id + " nao encontrado")).when(clienteController).buscarClientePorId(id);

            mockMvc.perform(get("/clientes/id/{id}", id)
                            .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.mensagem").value("Cliente com id " + id + " nao encontrado"));
        }

        @Test
        void deveBuscarClientePorNome() throws Exception {
            String nome = "João";
            when(clienteController.buscarClientePorNome(nome)).thenReturn(clienteDTO);

            mockMvc.perform(get("/clientes/nome/{nome}", nome)
                            .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.nome").value("Jorge"))
                    .andExpect(jsonPath("$.cpf").value("123.456.789-09"))
                    .andExpect(jsonPath("$.dataNascimento").value("07/12/2015"))
                    .andExpect(jsonPath("$.endereco").value("Rua Teste, 123, Bairro XYZ"))
                    .andExpect(jsonPath("$.numeroContato").value("(99) 99999-9999"))
                    .andExpect(jsonPath("$.email").value("email@email.com"))
                    .andExpect(jsonPath("$.infoPagamento").value("Cartao de Credito"));
        }

        @Test
        void deveRetornarNotFoundAoBuscarNomeInexistente() throws Exception {
            String nome = "NomeInexistente";
            doThrow(new ClienteNaoEncontradoException("Cliente com nome " + nome + " nao encontrado")).when(clienteController).buscarClientePorNome(nome);

            mockMvc.perform(get("/clientes/nome/{nome}", nome)
                            .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.mensagem").value("Cliente com nome " + nome + " nao encontrado"));
        }

        @Test
        void deveBuscarClientePorCpf() throws Exception {
            String cpf = "123.456.789-00";
            when(clienteController.buscarClientePorCpf(cpf)).thenReturn(clienteDTO);

            mockMvc.perform(get("/clientes/cpf/{cpf}", cpf)
                            .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.nome").value("Jorge"))
                    .andExpect(jsonPath("$.cpf").value("123.456.789-09"))
                    .andExpect(jsonPath("$.dataNascimento").value("07/12/2015"))
                    .andExpect(jsonPath("$.endereco").value("Rua Teste, 123, Bairro XYZ"))
                    .andExpect(jsonPath("$.numeroContato").value("(99) 99999-9999"))
                    .andExpect(jsonPath("$.email").value("email@email.com"))
                    .andExpect(jsonPath("$.infoPagamento").value("Cartao de Credito"));
        }

        @Test
        void deveRetornarNotFoundAoBuscarCpfInexistente() throws Exception {
            String cpf = "000.000.000-00";
            doThrow(new ClienteNaoEncontradoException("Cliente com cpf " + cpf + " nao encontrado")).when(clienteController).buscarClientePorCpf(cpf);

            mockMvc.perform(get("/clientes/cpf/{cpf}", cpf)
                            .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.mensagem").value("Cliente com cpf " + cpf + " nao encontrado"));
        }

        @Test
        void listarTodosClientes() throws Exception {
            List<ClienteDTO> clientes = List.of(clienteDTO);
            when(clienteController.listarTodosClientes()).thenReturn(clientes);

            mockMvc.perform(get("/clientes")
                            .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(1L))
                    .andExpect(jsonPath("$[0].nome").value("Jorge"))
                    .andExpect(jsonPath("$[0].cpf").value("123.456.789-09"))
                    .andExpect(jsonPath("$[0].dataNascimento").value("07/12/2015"))
                    .andExpect(jsonPath("$[0].endereco").value("Rua Teste, 123, Bairro XYZ"))
                    .andExpect(jsonPath("$[0].numeroContato").value("(99) 99999-9999"))
                    .andExpect(jsonPath("$[0].email").value("email@email.com"))
                    .andExpect(jsonPath("$[0].infoPagamento").value("Cartao de Credito"));
        }
    }

    @Nested
    class CriarClientesCases {
        @Test
        void criarClienteComSucesso() throws Exception {
            when(clienteController.criarCliente(any())).thenReturn(clienteDTO);

            mockMvc.perform(post("/clientes/create")
                            .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                            .content(asJsonString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.nome").value("Jorge"))
                    .andExpect(jsonPath("$.cpf").value("123.456.789-09"))
                    .andExpect(jsonPath("$.dataNascimento").value("07/12/2015"))
                    .andExpect(jsonPath("$.endereco").value("Rua Teste, 123, Bairro XYZ"))
                    .andExpect(jsonPath("$.numeroContato").value("(99) 99999-9999"))
                    .andExpect(jsonPath("$.email").value("email@email.com"))
                    .andExpect(jsonPath("$.infoPagamento").value("Cartao de Credito"));
        }

        @Test
        void criarClienteJaExistente() throws Exception {
            doThrow(new CpfJaCadastradoException("Cliente ja cadastrado")).when(clienteController).criarCliente(any(CriarClienteDTO.class));

            mockMvc.perform(post("/clientes/create")
                            .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                            .content(asJsonString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.mensagem").value("Cliente ja cadastrado"));
        }
    }

    @Nested
    class AtualizarClientesCases {
        @Test
        void editarCliente() throws Exception{
            Long id = 1L;
            when(clienteController.atualizarCliente(any(ClienteDTO.class))).thenReturn(clienteDTO);

            mockMvc.perform(put("/clientes/update/{id}", id)
                            .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                            .content(asJsonString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(id))
                    .andExpect(jsonPath("$.nome").value("Jorge"))
                    .andExpect(jsonPath("$.cpf").value("123.456.789-09"))
                    .andExpect(jsonPath("$.dataNascimento").value("07/12/2015"))
                    .andExpect(jsonPath("$.endereco").value("Rua Teste, 123, Bairro XYZ"))
                    .andExpect(jsonPath("$.numeroContato").value("(99) 99999-9999"))
                    .andExpect(jsonPath("$.email").value("email@email.com"))
                    .andExpect(jsonPath("$.infoPagamento").value("Cartao de Credito"));
        }

        @Test
        void editarClienteNaoEncontrado() throws Exception{
            Long id = 1L;
            doThrow(new ClienteNaoEncontradoException("Cliente nao encontrado")).when(clienteController).atualizarCliente(any(ClienteDTO.class));

            mockMvc.perform(put("/clientes/update/{id}", id)
                            .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                            .content(asJsonString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.mensagem").value("Cliente nao encontrado"));
        }
    }

    @Nested
    class DeletarClientesCases {
        @Test
        void deletarCliente() throws Exception{
            Long id = 1L;
            doNothing().when(clienteController).removerCliente(id);

            mockMvc.perform(delete("/clientes/delete/{id}", id)
                    .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                    .andDo(print())
                    .andExpect(status().isNoContent());
        }

        @Test
        void deletarClienteNaoEncontrado() throws Exception{
            Long id = 1L;
            doThrow(new ClienteNaoEncontradoException("Cliente nao encontrado")).when(clienteController).removerCliente(id);

            mockMvc.perform(delete("/clientes/delete/{id}", id)
                            .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                            .content(asJsonString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.mensagem").value("Cliente nao encontrado"));
        }
    }

    private String asJsonString(final Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
