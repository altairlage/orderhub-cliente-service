package com.fiap.orderhubClienteService.configuration;

import br.com.orderhub.core.controller.ClienteController;
import br.com.orderhub.core.interfaces.IClienteGateway;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ClienteControllerConfigTest {

    @Test
    void testProdutoControllerBeanCreation() {
        IClienteGateway mockGateway = mock(IClienteGateway.class);
        ClienteControllerConfig config = new ClienteControllerConfig();
        ClienteController controller = config.clienteController(mockGateway);
        assertNotNull(controller);
    }
}