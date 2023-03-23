package com.algaworks.algamoney_api.event;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEvent;

public class RecursoCriadoEvent extends ApplicationEvent {

    private HttpServletResponse response;
    private Integer codigo;

    public RecursoCriadoEvent(Object source, HttpServletResponse response, Integer codigo) {
        super(source);
        this.response = response;
        this.codigo = codigo;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public Integer getCodigo() {
        return codigo;
    }
}
