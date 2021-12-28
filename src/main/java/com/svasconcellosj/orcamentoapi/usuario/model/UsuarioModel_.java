package com.svasconcellosj.orcamentoapi.usuario.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UsuarioModel.class)
public abstract class UsuarioModel_ {

	public static volatile SingularAttribute<UsuarioModel, String> senha;
	public static volatile ListAttribute<UsuarioModel, PermissaoModel> permissoes;
	public static volatile SingularAttribute<UsuarioModel, Long> id;
	public static volatile SingularAttribute<UsuarioModel, String> nome;
	public static volatile SingularAttribute<UsuarioModel, String> email;

}

