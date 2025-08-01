package com.fiap.orderhubClienteService.gateway;

import br.com.orderhub.core.domain.entities.Cliente;
import br.com.orderhub.core.exceptions.ClienteNaoEncontradoException;
import br.com.orderhub.core.interfaces.IClienteGateway;
import com.fiap.orderhubClienteService.mapper.ClienteEntityMapper;
import com.fiap.orderhubClienteService.persistence.ClienteEntity;
import com.fiap.orderhubClienteService.persistence.ClienteRepository;
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
        Long id = cliente.getId();

        ClienteEntity clienteEntity = clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException("Cliente com id " + id + " nao foi encontrado"));

        clienteEntity.setNome(cliente.getNome());
        clienteEntity.setCpf(cliente.getCpf());
        clienteEntity.setDataNascimento(cliente.getDataNascimento());
        clienteEntity.setEndereco(cliente.getEndereco());
        clienteEntity.setNumeroContato(cliente.getNumeroContato());
        clienteEntity.setEmail(cliente.getEmail());
        clienteEntity.setInfoPagamento(cliente.getInfoPagamento());

        ClienteEntity clienteEntityAtualizado = clienteRepository.save(clienteEntity);

        return ClienteEntityMapper.entityToDomain(clienteEntityAtualizado);
    }

    @Override
    public void remover(Long id) throws ClienteNaoEncontradoException {
        if(!clienteRepository.existsById(id)){
            throw new ClienteNaoEncontradoException("Cliente com id " + id + " nao foi encontrado");
        }

        clienteRepository.deleteById(id);
    }

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll()
                .stream().map(ClienteEntityMapper::entityToDomain)
                .toList();
    }
}
