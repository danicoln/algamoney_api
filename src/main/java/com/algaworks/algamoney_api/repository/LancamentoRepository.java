package com.algaworks.algamoney_api.repository;

import com.algaworks.algamoney_api.model.Lancamento;
import com.algaworks.algamoney_api.repository.lancamento.LancamentoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Integer>, LancamentoRepositoryQuery {
}
