package com.fiap.orderhubClienteService.mapper;

import br.com.orderhub.core.domain.entities.Cliente;
import com.fiap.orderhubClienteService.persistence.ClienteEntity;
import com.fiap.orderhubClienteService.utils.ClienteServiceUtilsTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClienteEntityMapperTest {

    @Test
    void entityToDomain() {
        Cliente cliente = ClienteEntityMapper.entityToDomain(ClienteServiceUtilsTest.criaClienteEntity());

        assertEquals("Jorge", cliente.getNome());
        assertEquals("123.456.789.00", cliente.getCpf());
        assertEquals("07/12/2015", cliente.getDataNascimento());
        assertEquals("Rua Teste, 123, Bairro XYZ", cliente.getEndereco());
        assertEquals("(99) 99999-9999", cliente.getNumeroContato());
        assertEquals("email@email.com", cliente.getEmail());
        assertEquals("Cartao de Credito", cliente.getInfoPagamento());
    }

    @Test
    void domainToEntity() {
        ClienteEntity cliente = ClienteEntityMapper.domainToEntity(ClienteServiceUtilsTest.criaCliente());

        assertEquals("Jorge", cliente.getNome());
        assertEquals("123.456.789.10", cliente.getCpf());
        assertEquals("07/12/2015", cliente.getDataNascimento());
        assertEquals("Rua Teste, 123, Bairro XYZ", cliente.getEndereco());
        assertEquals("(99) 99999-9999", cliente.getNumeroContato());
        assertEquals("email@email.com", cliente.getEmail());
        assertEquals("Cartao de Credito", cliente.getInfoPagamento());
    }
}