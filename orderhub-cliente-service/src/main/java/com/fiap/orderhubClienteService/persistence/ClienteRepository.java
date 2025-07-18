package com.fiap.orderhubClienteService.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    @Query(value = "SELECT * FROM clientes WHERE nome ILIKE CONCAT('%', :nome, '%');", nativeQuery = true)
    Optional<ClienteEntity> findByNome(@Param("nome") String nome);

    Optional<ClienteEntity> findByCpf(String cpf);
}
