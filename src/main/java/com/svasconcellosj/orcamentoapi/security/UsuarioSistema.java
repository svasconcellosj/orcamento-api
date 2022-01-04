package com.svasconcellosj.orcamentoapi.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.svasconcellosj.orcamentoapi.usuario.model.UsuarioModel;

public class UsuarioSistema extends User {
    private static final long serialVersionUID = 1L;

	private UsuarioModel usuario;

	public UsuarioSistema(UsuarioModel usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getEmail(), usuario.getSenha(), authorities);
		this.usuario = usuario;
	}

	public UsuarioModel getUsuario() {
		return usuario;
	}
}
