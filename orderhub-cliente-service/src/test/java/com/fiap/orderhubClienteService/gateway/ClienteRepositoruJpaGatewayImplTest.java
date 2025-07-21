package com.fiap.orderhubClienteService.gateway;

import br.com.orderhub.core.domain.entities.Cliente;
import com.fiap.orderhubClienteService.persistence.ClienteEntity;
import com.fiap.orderhubClienteService.persistence.ClienteRepository;
import com.fiap.orderhubClienteService.utils.ClienteServiceUtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteRepositoruJpaGatewayImplTest {

    private ClienteRepository clienteRepository;
    private ClienteRepositoruJpaGatewayImpl gateway;

    @BeforeEach
    void setup() {
        clienteRepository = mock(ClienteRepository.class);
        gateway = new ClienteRepositoruJpaGatewayImpl(clienteRepository);
    }

    @Test
    void buscarPorId_Sucesso() {
        Long id = 1L;

        when(clienteRepository.findById(id)).thenReturn(Optional.of(ClienteServiceUtilsTest.criaClienteEntity()));

        Cliente cliente = gateway.buscarPorId(id);

        assertNotNull(cliente);
        assertEquals("Jorge", cliente.getNome());
        assertEquals("123.456.789-09", cliente.getCpf());
        assertEquals("07/12/2015", cliente.getDataNascimento());
        assertEquals("Rua Teste, 123, Bairro XYZ", cliente.getEndereco());
        assertEquals("(99) 99999-9999", cliente.getNumeroContato());
        assertEquals("email@email.com", cliente.getEmail());
        assertEquals("Cartao de Credito", cliente.getInfoPagamento());
    }

    @Test
    void buscarPorId_ClienteNaoEncontrado() {
        Long idInexistente = 2L;

        when(clienteRepository.findById(idInexistente)).thenReturn(Optional.empty());

        Cliente cliente = gateway.buscarPorId(idInexistente);

        assertNull(cliente);
    }

    @Test
    void buscarPorCpf() {
       String cpf = "123.456.789-09";

        when(clienteRepository.findByCpf(cpf)).thenReturn(Optional.of(ClienteServiceUtilsTest.criaClienteEntity()));

        Cliente cliente = gateway.buscarPorCpf(cpf);

        assertNotNull(cliente);
        assertEquals("Jorge", cliente.getNome());
        assertEquals("123.456.789-09", cliente.getCpf());
        assertEquals("07/12/2015", cliente.getDataNascimento());
        assertEquals("Rua Teste, 123, Bairro XYZ", cliente.getEndereco());
        assertEquals("(99) 99999-9999", cliente.getNumeroContato());
        assertEquals("email@email.com", cliente.getEmail());
        assertEquals("Cartao de Credito", cliente.getInfoPagamento());
    }

    @Test
    void buscarPorCpf_ClienteNaoEncontrado() {
        String cpfInexistente = "123.456.789-08";

        when(clienteRepository.findByCpf(cpfInexistente)).thenReturn(Optional.empty());

        Cliente cliente = gateway.buscarPorCpf(cpfInexistente);

        assertNull(cliente);
    }

    @Test
    void buscarPorNome() {
        String nome = "Jorge";

        when(clienteRepository.findByNome(nome)).thenReturn(Optional.of(ClienteServiceUtilsTest.criaClienteEntity()));

        Cliente cliente = gateway.buscarPorNome(nome);

        assertNotNull(cliente);
        assertEquals("Jorge", cliente.getNome());
        assertEquals("123.456.789-09", cliente.getCpf());
        assertEquals("07/12/2015", cliente.getDataNascimento());
        assertEquals("Rua Teste, 123, Bairro XYZ", cliente.getEndereco());
        assertEquals("(99) 99999-9999", cliente.getNumeroContato());
        assertEquals("email@email.com", cliente.getEmail());
        assertEquals("Cartao de Credito", cliente.getInfoPagamento());
    }

    @Test
    void buscarPorNome_ClienteNaoEncontrado() {
        String nomeInexistente = "Jonas";

        when(clienteRepository.findByNome(nomeInexistente)).thenReturn(Optional.empty());

        Cliente cliente = gateway.buscarPorNome(nomeInexistente);

        assertNull(cliente);
    }

    @Test
    void criarCliente() {
        ClienteEntity clienteEntity = ClienteServiceUtilsTest.criaClienteEntity();

        when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(clienteEntity);

        Cliente cliente = ClienteServiceUtilsTest.criaCliente();

        Cliente resultado = gateway.criar(cliente);

        assertNotNull(resultado);
        assertEquals("Jorge", resultado.getNome());
        assertEquals("123.456.789-09", resultado.getCpf());
        assertEquals("07/12/2015", resultado.getDataNascimento());
        assertEquals("Rua Teste, 123, Bairro XYZ", resultado.getEndereco());
        assertEquals("(99) 99999-9999", resultado.getNumeroContato());
        assertEquals("email@email.com", resultado.getEmail());
        assertEquals("Cartao de Credito", resultado.getInfoPagamento());

        ArgumentCaptor<ClienteEntity> captor = ArgumentCaptor.forClass(ClienteEntity.class);
        verify(clienteRepository, times(1)).save(captor.capture());
    }

    @Test
    void atualizar() {
        ClienteEntity clienteEntity = ClienteServiceUtilsTest.criaClienteEntity();
        clienteEntity.setNome("Jorge Santos");

        when(clienteRepository.findById(clienteEntity.getId())).thenReturn(Optional.of(clienteEntity));
        when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(clienteEntity);

        Cliente cliente = ClienteServiceUtilsTest.criaCliente();
        cliente.setNome("Jorge Santos");

        Cliente resultado = gateway.atualizar(cliente);

        assertNotNull(resultado);
        assertEquals("Jorge Santos", resultado.getNome());
        assertEquals("123.456.789-09", resultado.getCpf());
        assertEquals("07/12/2015", resultado.getDataNascimento());
        assertEquals("Rua Teste, 123, Bairro XYZ", resultado.getEndereco());
        assertEquals("(99) 99999-9999", resultado.getNumeroContato());
        assertEquals("email@email.com", resultado.getEmail());
        assertEquals("Cartao de Credito", resultado.getInfoPagamento());

        verify(clienteRepository, times(1)).findById(clienteEntity.getId());
        verify(clienteRepository, times(1)).save(clienteEntity);
    }

    @Test
    void remover() {
        Long id = 1L;
        when(clienteRepository.existsById(id)).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(id);

        gateway.remover(id);

        verify(clienteRepository).existsById(id);
        verify(clienteRepository).deleteById(id);
    }

    @Test
    void listarTodos() {
        List<ClienteEntity> entidades = List.of(ClienteServiceUtilsTest.criaClienteEntity());

        when(clienteRepository.findAll()).thenReturn(entidades);

        List<Cliente> resultado = gateway.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        Cliente cliente = resultado.get(0);
        assertEquals("Jorge", cliente.getNome());
        assertEquals("123.456.789-09", cliente.getCpf());
        assertEquals("07/12/2015", cliente.getDataNascimento());
        assertEquals("Rua Teste, 123, Bairro XYZ", cliente.getEndereco());
        assertEquals("(99) 99999-9999", cliente.getNumeroContato());
        assertEquals("email@email.com", cliente.getEmail());
        assertEquals("Cartao de Credito", cliente.getInfoPagamento());

        verify(clienteRepository, times(1)).findAll();
    }
}