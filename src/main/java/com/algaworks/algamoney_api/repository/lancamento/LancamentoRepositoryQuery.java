package com.algaworks.algamoney_api.repository.lancamento;

import com.algaworks.algamoney_api.model.Lancamento;
import com.algaworks.algamoney_api.repository.filter.LancamentoFilter;

import java.util.List;

public interface LancamentoRepositoryQuery {

    public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);

}
