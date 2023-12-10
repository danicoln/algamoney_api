package com.algaworks.algamoney_api.repository;

import com.algaworks.algamoney_api.domain.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
