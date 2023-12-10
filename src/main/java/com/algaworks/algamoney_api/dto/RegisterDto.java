package com.algaworks.algamoney_api.dto;

import com.algaworks.algamoney_api.domain.model.UsuarioRole;

public record RegisterDto(String nome, String login, String password, UsuarioRole role) {
}
