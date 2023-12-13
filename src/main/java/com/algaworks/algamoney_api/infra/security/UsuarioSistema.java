package com.algaworks.algamoney_api.infra.security;

import com.algaworks.algamoney_api.domain.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UsuarioSistema extends User {

    private Usuario usuario;

    public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getLogin(), usuario.getPassword(), authorities);
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
