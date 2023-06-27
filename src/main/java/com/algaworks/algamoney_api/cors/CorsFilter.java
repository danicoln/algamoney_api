package com.algaworks.algamoney_api.cors;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

    /**
     * Classe referente a aula 6.10. Criando filtro para CORS*/

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    private String origemPermitida = "http://localhost:4200"; //TODO: Configurar para diferentes ambientes

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        res.setHeader("Access-Control-Allow-Origin", origemPermitida);
        res.setHeader("Access-Control-Allow-Credentials", "true");


        if("OPTIONS".equals(req.getMethod()) && origemPermitida.equals(req.getHeader("Origin"))) {

            res.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
            res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
            res.setHeader("Access-Control-Max-Age", "3600");

            res.setStatus(HttpServletResponse.SC_OK);
        }
        else{
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
