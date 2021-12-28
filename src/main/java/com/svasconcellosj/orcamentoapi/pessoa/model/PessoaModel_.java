package com.svasconcellosj.orcamentoapi.pessoa.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.svasconcellosj.orcamentoapi.model.EnderecoModel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PessoaModel.class)
public abstract class PessoaModel_ {

	public static volatile SingularAttribute<PessoaModel, Long> id;
	public static volatile SingularAttribute<PessoaModel, Boolean> ativo;
	public static volatile SingularAttribute<PessoaModel, EnderecoModel> endereco;
	public static volatile SingularAttribute<PessoaModel, String> nome;

}

