package com.svasconcellosj.orcamentoapi.lancamento.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.svasconcellosj.orcamentoapi.categoria.model.CategoriaModel;
import com.svasconcellosj.orcamentoapi.pessoa.model.PessoaModel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LancamentoModel.class)
public abstract class LancamentoModel_ {

	public static volatile SingularAttribute<LancamentoModel, Long> id;
	public static volatile SingularAttribute<LancamentoModel, String> descricao;
	public static volatile SingularAttribute<LancamentoModel, String> observacao;
	public static volatile SingularAttribute<LancamentoModel, TipoLancamento> tipo;
	public static volatile SingularAttribute<LancamentoModel, LocalDate> dataPagamento;
	public static volatile SingularAttribute<LancamentoModel, PessoaModel> pessoa;
	public static volatile SingularAttribute<LancamentoModel, LocalDate> dataVencimento;
	public static volatile SingularAttribute<LancamentoModel, CategoriaModel> categoria;
	public static volatile SingularAttribute<LancamentoModel, BigDecimal> valor;

}

