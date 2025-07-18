package com.fiap.orderhubClienteService.dtos;

public record ClienteApiResponseDto(
        Long id,
        String nome,
        String cpf,
        String dataNascimento,
        String endereco,
        String numeroContato,
        String email,
        String infoPagamento
) {
}
