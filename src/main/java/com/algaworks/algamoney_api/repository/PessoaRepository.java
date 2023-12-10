package com.algaworks.algamoney_api.repository;

import com.algaworks.algamoney_api.domain.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    public Page<Pessoa> findByNomeContaining(String nome, Pageable pageable);
}
