package com.svasconcellosj.orcamentoapi.repository.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.svasconcellosj.orcamentoapi.model.PessoaModel;
import com.svasconcellosj.orcamentoapi.repository.filter.PessoaFilter;

public interface PessoaRepositoryQuery {

	public Page<PessoaModel> filtrar(PessoaFilter pessoaFilter, Pageable pageable);
	
}
