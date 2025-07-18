package com.fiap.orderhubClienteService.mapper;

import br.com.orderhub.core.domain.entities.Cliente;
import com.fiap.orderhubClienteService.persistence.ClienteEntity;

public class ClienteEntityMapper {
    public ClienteEntityMapper() {}

    public static Cliente entityToDomain(ClienteEntity clienteEntity) {
        return new Cliente(
                clienteEntity.getId(),
                clienteEntity.getNome(),
                clienteEntity.getCpf(),
                clienteEntity.getDataNascimento(),
                clienteEntity.getEndereco(),
                clienteEntity.getNumeroContato(),
                clienteEntity.getEmail(),
                clienteEntity.getInfoPagamento()
        );
    }

    public static ClienteEntity domainToEntity(Cliente cliente) {
        return new ClienteEntity(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getDataNascimento(),
                cliente.getEndereco(),
                cliente.getNumeroContato(),
                cliente.getEmail(),
                cliente.getInfoPagamento()
        );
    }
}
