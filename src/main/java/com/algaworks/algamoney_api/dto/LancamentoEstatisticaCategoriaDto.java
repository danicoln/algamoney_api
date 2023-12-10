package com.algaworks.algamoney_api.dto;

import com.algaworks.algamoney_api.domain.model.Categoria;

import java.math.BigDecimal;

public class LancamentoEstatisticaCategoriaDto {

    /**
     * Esta classe faz a somatória de gastos por categoria
     * */

    private Categoria categoria;
    private BigDecimal total;

    public LancamentoEstatisticaCategoriaDto(Categoria categoria, BigDecimal total) {
        this.categoria = categoria;
        this.total = total;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
