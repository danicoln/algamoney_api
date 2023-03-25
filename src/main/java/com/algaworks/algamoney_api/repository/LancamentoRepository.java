package com.algaworks.algamoney_api.repository;

import com.algaworks.algamoney_api.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Integer> {
}
