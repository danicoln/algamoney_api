package com.algaworks.algamoney_api.repository;

import com.algaworks.algamoney_api.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

}
