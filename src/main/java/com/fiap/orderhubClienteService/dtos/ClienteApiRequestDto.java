package com.fiap.orderhubClientService.dtos;

public record ClienteApiRequestDto(
        String nome,
        String cpf,
        String dataNascimento,
        String endereco,
        String numeroContato,
        String email,
        String infoPagamento
) {
}
