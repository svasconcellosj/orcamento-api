package com.svasconcellosj.orcamentoapi.usuario.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class UsuarioModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min = 3, max = 30)
	private String nome;
	
	@NotNull
	private String email;
	
	@NotNull
	private String senha;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuarios_permissoes", joinColumns = @JoinColumn(name = "id_usuario")	, inverseJoinColumns = @JoinColumn(name = "id_permissao"))
	private List<PermissaoModel> permissoes;
	
}
