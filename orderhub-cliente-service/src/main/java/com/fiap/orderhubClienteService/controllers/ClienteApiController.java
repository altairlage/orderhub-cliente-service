package com.fiap.orderhubClienteService.controllers;

import br.com.orderhub.core.controller.ClienteController;
import br.com.orderhub.core.dto.clientes.ClienteDTO;
import br.com.orderhub.core.dto.clientes.CriarClienteDTO;
import com.fiap.orderhubClienteService.dtos.ClienteApiRequestDto;
import com.fiap.orderhubClienteService.dtos.ClienteApiResponseDto;
import com.fiap.orderhubClienteService.mapper.ClienteApiDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteApiController {

    private final ClienteController clienteController;

    public ClienteApiController(ClienteController clienteController) {
        this.clienteController = clienteController;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ClienteApiResponseDto> buscarClientePorId(@PathVariable Long id) {
        return ResponseEntity.ok(ClienteApiDtoMapper.clienteDtoToResponseDto(clienteController.buscarClientePorId(id)));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<ClienteApiResponseDto> buscarClientePorNome(@PathVariable String nome) {
        return ResponseEntity.ok(ClienteApiDtoMapper.clienteDtoToResponseDto(clienteController.buscarClientePorNome(nome)));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteApiResponseDto> buscarClientePorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(ClienteApiDtoMapper.clienteDtoToResponseDto(clienteController.buscarClientePorCpf(cpf)));
    }

    @GetMapping()
    public ResponseEntity<List<ClienteApiResponseDto>> listarTodosClientes() {
        List<ClienteDTO> clientes = clienteController.listarTodosClientes();

        List<ClienteApiResponseDto> responses = clientes
                .stream()
                .map(ClienteApiDtoMapper::clienteDtoToResponseDto)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/create")
    public ResponseEntity<ClienteApiResponseDto> criarCliente(@RequestBody ClienteApiRequestDto requestDto) {
        CriarClienteDTO criarClienteDTO = ClienteApiDtoMapper.requestDtoToCriarClienteDto(requestDto);
        ClienteDTO clienteDTO = clienteController.criarCliente(criarClienteDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ClienteApiDtoMapper.clienteDtoToResponseDto(clienteDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClienteApiResponseDto> editarCliente(@PathVariable Long id, @RequestBody ClienteApiRequestDto requestDto) {
        ClienteDTO clienteDTO = ClienteApiDtoMapper.requestDtoToClienteDto(id, requestDto);
        ClienteDTO clienteAtualizado = clienteController.atualizarCliente(clienteDTO);

        return ResponseEntity.ok(ClienteApiDtoMapper.clienteDtoToResponseDto(clienteAtualizado));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        clienteController.removerCliente(id);
        return ResponseEntity.noContent().build();
    }
}
