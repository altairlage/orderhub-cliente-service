package com.fiap.orderhubClientService.gateway;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.exceptions.ClienteNaoEncontradoException;
import br.com.orderhub.core.interfaces.IClienteGateway;
import com.fiap.orderhubClientService.mapper.ClienteEntityMapper;
import com.fiap.orderhubClientService.persistence.ClienteEntity;
import com.fiap.orderhubClientService.persistence.ClienteRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClienteRepositoruJpaGatewayImpl implements IClienteGateway {

    private final ClienteRepository clienteRepository;

    public ClienteRepositoruJpaGatewayImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .map(ClienteEntityMapper::entityToDomain)
                .orElse(null);
    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf)
                .map(ClienteEntityMapper::entityToDomain)
                .orElse(null);
    }

    @Override
    public Cliente buscarPorNome(String nome) {
        return clienteRepository.findByNome(nome)
                .map(ClienteEntityMapper::entityToDomain)
                .orElse(null);
    }

    @Override
    public Cliente criar(Cliente cliente) {
        ClienteEntity clienteEntity = ClienteEntityMapper.domainToEntity(cliente);
        return ClienteEntityMapper.entityToDomain(clienteRepository.save(clienteEntity));
    }

    @Override
    public Cliente atualizar(Cliente cliente) throws ClienteNaoEncontradoException {
        ClienteEntity clienteEntity = ClienteEntityMapper.domainToEntity(cliente);
        clienteRepository.save(clienteEntity);
        return ClienteEntityMapper.entityToDomain(clienteEntity);
    }

    @Override
    public void remover(Long id) throws ClienteNaoEncontradoException {
        clienteRepository.deleteById(id);
    }

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll()
                .stream().map(ClienteEntityMapper::entityToDomain)
                .toList();
    }
}
