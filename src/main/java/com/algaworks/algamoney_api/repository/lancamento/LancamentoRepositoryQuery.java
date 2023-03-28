package com.algaworks.algamoney_api.repository.lancamento;

import com.algaworks.algamoney_api.model.Lancamento;
import com.algaworks.algamoney_api.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LancamentoRepositoryQuery {

    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
}
