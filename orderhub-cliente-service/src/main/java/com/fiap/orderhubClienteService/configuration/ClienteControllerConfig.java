package com.fiap.orderhubClienteService.configuration;

import br.com.orderhub.core.controller.ClienteController;
import br.com.orderhub.core.interfaces.IClienteGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClienteControllerConfig {

    @Bean
    public ClienteController clienteController(IClienteGateway clienteGateway) {
        return new ClienteController(clienteGateway);
    }
}
