package com.algaworks.algamoney_api.repository;

import com.algaworks.algamoney_api.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    UserDetails findByLogin(String login);
}
