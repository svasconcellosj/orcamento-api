package com.svasconcellosj.orcamentoapi.pessoa.repository.consult;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.svasconcellosj.orcamentoapi.pessoa.model.PessoaModel;
import com.svasconcellosj.orcamentoapi.pessoa.repository.filter.PessoaFilter;

public interface PessoaRepositoryQuery {

	public Page<PessoaModel> filtrar(PessoaFilter pessoaFilter, Pageable pageable);
	
}
