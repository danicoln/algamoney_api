package com.algaworks.algamoney_api.repository.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class LancamentoFilter {

    private String descricao;

    //    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVendimentoAte;

    //    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVendimentoDe;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataVendimentoDe() {
        return dataVendimentoDe;
    }

    public void setDataVendimentoDe(LocalDate dataVendimentoDe) {
        this.dataVendimentoDe = dataVendimentoDe;
    }

    public LocalDate getDataVendimentoAte() {
        return dataVendimentoAte;
    }

    public void setDataVendimentoAte(LocalDate dataVendimentoAte) {
        this.dataVendimentoAte = dataVendimentoAte;
    }
}
