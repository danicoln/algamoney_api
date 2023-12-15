package com.algaworks.algamoney_api.repository.lancamento;

import com.algaworks.algamoney_api.domain.model.Lancamento;
import com.algaworks.algamoney_api.repository.filter.LancamentoFilter;
import com.algaworks.algamoney_api.repository.projection.ResumoLancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRepositoryQuery {

    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);

    /**
     * referente: 7.1. Implementando projeção de lançamento*/
    public Page<ResumoLancamento> resumir(LancamentoFilter filter, Pageable pageable);
}
