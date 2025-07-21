package com.fiap.orderhubClienteService.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.orderhubClienteService.OrderhubClienteServiceApplication;
import com.fiap.orderhubClienteService.dtos.ClienteApiRequestDto;
import com.fiap.orderhubClienteService.utils.ClienteServiceUtilsTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = OrderhubClienteServiceApplication.class)
@ActiveProfiles("teste")
public class ClienteApiControllerIT {

    @LocalServerPort
    private int port;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void deveCriarClienteComSucesso() throws JsonProcessingException {
        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(ClienteServiceUtilsTest.criaClienteApiRequestDto()))
                .when()
                .post("/clientes/create")
                .then()
                .statusCode(201)
                .body("nome", equalTo("Jorge"))
                .body("cpf", equalTo("123.456.789-09"))
                .body("dataNascimento", equalTo("07/12/2015"))
                .body("endereco", equalTo("Rua Teste, 123, Bairro XYZ"))
                .body("numeroContato", equalTo("(99) 99999-9999"))
                .body("email", equalTo("email@email.com"))
                .body("infoPagamento", equalTo("Cartao de Credito"));
    }

    @Test
    void deveRetornarConflitoAoCriarClienteJaExistente() throws JsonProcessingException {
        ClienteApiRequestDto cliente = ClienteServiceUtilsTest.criaClienteApiRequestDto();

        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(cliente))
                .when()
                .post("/clientes/create")
                .then()
                .statusCode(201);

        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(cliente))
                .when()
                .post("/clientes/create")
                .then()
                .statusCode(409)
                .body("mensagem", containsString("O CPF 123.456.789-09 ja esta sendo usado em outro cadastro"));
    }

    @Test
    void deveRetornarClientePorIdComSucesso() throws JsonProcessingException {
        ClienteApiRequestDto cliente = ClienteServiceUtilsTest.criaClienteApiRequestDto();

        String id = given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(cliente))
                .when()
                .post("/clientes/create")
                .then()
                .statusCode(201)
                .extract()
                .path("id")
                .toString();

        given()
                .when()
                .get("/clientes/id/{id}", id)
                .then()
                .statusCode(200)
                .body("cpf", equalTo("123.456.789-09"));
    }

    @Test
    void deveRetornarNotFoundExceptionAoBuscarClientePorId() {
        given()
                .when()
                .get("/clientes/id/{id}", 99999L)
                .then()
                .statusCode(404)
                .body("mensagem", containsString("Cliente com ID 99999 nao foi encontrado"));
    }

    @Test
    void deveRetornarClientePorNomeComSucesso() throws JsonProcessingException {
        ClienteApiRequestDto cliente = ClienteServiceUtilsTest.criaClienteApiRequestDto();

        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(cliente))
                .when()
                .post("/clientes/create")
                .then()
                .statusCode(201);

        given()
                .when()
                .get("/clientes/nome/{nome}", "Jorge")
                .then()
                .statusCode(200)
                .body("cpf", equalTo("123.456.789-09"));
    }

    @Test
    void deveRetornarNotFoundExceptionAoBuscarClientePorNome() {
        given()
                .when()
                .get("/clientes/nome/{nome}", "Inexistente")
                .then()
                .statusCode(404)
                .body("mensagem", containsString("Cliente nao encontrado"));
    }

    @Test
    void deveRetornarClientePorCpfComSucesso() throws JsonProcessingException {
        ClienteApiRequestDto cliente = ClienteServiceUtilsTest.criaClienteApiRequestDto();

        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(cliente))
                .when()
                .post("/clientes/create")
                .then()
                .statusCode(201);

        given()
                .when()
                .get("/clientes/cpf/{cpf}", "123.456.789-09")
                .then()
                .statusCode(200)
                .body("nome", equalTo("Jorge"));
    }

    @Test
    void deveRetornarNotFoundExceptionAoBuscarClientePorCpf() {
        given()
                .when()
                .get("/clientes/cpf/{cpf}", "000.000.000-00")
                .then()
                .statusCode(404)
                .body("mensagem", containsString("Cliente com cpf: 000.000.000-00 nao encontrado"));
    }

    @Test
    void deveAlterarClienteComSucesso() throws JsonProcessingException {
        ClienteApiRequestDto cliente = ClienteServiceUtilsTest.criaClienteApiRequestDto();

        String id = given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(cliente))
                .when()
                .post("/clientes/create")
                .then()
                .statusCode(201)
                .extract()
                .path("id")
                .toString();

        ClienteApiRequestDto clienteAtualizado = new ClienteApiRequestDto(
                "Nome Atualizado",
                cliente.cpf(),
                cliente.dataNascimento(),
                cliente.endereco(),
                cliente.numeroContato(),
                cliente.email(),
                cliente.infoPagamento()
        );

        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(clienteAtualizado))
                .when()
                .put("/clientes/update/{id}", id)
                .then()
                .statusCode(200)
                .body("nome", equalTo("Nome Atualizado"));
    }

    @Test
    void deveRetornarNotFoundExceptionAoAlterarClienteInexistente() throws JsonProcessingException {
        ClienteApiRequestDto cliente = ClienteServiceUtilsTest.criaClienteApiRequestDto();

        given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(cliente))
                .when()
                .put("/clientes/update/{id}", 99999L)
                .then()
                .statusCode(404)
                .body("mensagem", containsString("Cliente com cpf: 123.456.789-09 não encontrado"));
    }

    @Test
    void deveDeletarClienteComSucesso() throws JsonProcessingException {
        ClienteApiRequestDto cliente = ClienteServiceUtilsTest.criaClienteApiRequestDto();

        String id = given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(cliente))
                .when()
                .post("/clientes/create")
                .then()
                .statusCode(201)
                .extract()
                .path("id")
                .toString();

        given()
                .when()
                .delete("/clientes/delete/{id}", id)
                .then()
                .statusCode(204);
    }

    @Test
    void deveRetornarNotFoundExceptionAoDeletarClienteInexistente() {
        given()
                .when()
                .delete("/clientes/delete/{id}", 99999L)
                .then()
                .statusCode(404)
                .body("mensagem", containsString("Cliente com ID 99999 nao foi encontrado"));
    }
}