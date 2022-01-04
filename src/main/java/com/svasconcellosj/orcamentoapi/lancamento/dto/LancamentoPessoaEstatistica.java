package com.svasconcellosj.orcamentoapi.lancamento.dto;

import java.math.BigDecimal;

import com.svasconcellosj.orcamentoapi.lancamento.model.TipoLancamento;
import com.svasconcellosj.orcamentoapi.pessoa.model.PessoaModel;

public class LancamentoPessoaEstatistica {

	private TipoLancamento tipo;

	private PessoaModel pessoaModel;

	private BigDecimal total;

	public LancamentoPessoaEstatistica(TipoLancamento tipo, PessoaModel pessoaModel, BigDecimal total) {
		this.tipo = tipo;
		this.pessoaModel = pessoaModel;
		this.total = total;
	}

	public TipoLancamento getTipo() {
		return tipo;
	}

	public void setTipo(TipoLancamento tipo) {
		this.tipo = tipo;
	}

	public PessoaModel getPessoaModel() {
		return pessoaModel;
	}

	public void setPessoaModel(PessoaModel pessoaModel) {
		this.pessoaModel = pessoaModel;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
