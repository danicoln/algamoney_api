package com.algaworks.algamoney_api.event.listener;

import com.algaworks.algamoney_api.event.RecursoCriadoEvent;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * 3.12. Usando eventos para adicionar header Location*/
@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

    @Override
    public void onApplicationEvent(RecursoCriadoEvent event) {
        HttpServletResponse response = event.getResponse();
        Integer codigo = event.getCodigo();

        adicionarHeaderLocation(response, codigo);
    }

    private void adicionarHeaderLocation(HttpServletResponse response, Integer codigo) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(codigo).toUri();
        response.setHeader("Location", uri.toASCIIString());
    }
}
