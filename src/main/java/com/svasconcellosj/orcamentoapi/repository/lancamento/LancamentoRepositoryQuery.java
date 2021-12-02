package com.svasconcellosj.orcamentoapi.repository.lancamento;

import com.svasconcellosj.orcamentoapi.model.LancamentoModel;
import com.svasconcellosj.orcamentoapi.repository.filter.LancamentoFilter;
import com.svasconcellosj.orcamentoapi.repository.projection.ResumoLancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface LancamentoRepositoryQuery {

	public Page<LancamentoModel> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);

}
