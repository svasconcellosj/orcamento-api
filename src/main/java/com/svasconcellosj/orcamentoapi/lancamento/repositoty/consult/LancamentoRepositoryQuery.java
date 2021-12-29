package com.svasconcellosj.orcamentoapi.lancamento.repositoty.consult;

import com.svasconcellosj.orcamentoapi.lancamento.dto.LancamentoCategoriaEstatistica;
import com.svasconcellosj.orcamentoapi.lancamento.dto.LancamentoDiaEstatistica;
import com.svasconcellosj.orcamentoapi.lancamento.model.LancamentoModel;
import com.svasconcellosj.orcamentoapi.lancamento.repositoty.filter.LancamentoFilter;
import com.svasconcellosj.orcamentoapi.lancamento.repositoty.projection.ResumoLancamento;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface LancamentoRepositoryQuery {

	public Page<LancamentoModel> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);

	public List<LancamentoCategoriaEstatistica> porCategoria(LocalDate mesReferencia);
	public List<LancamentoDiaEstatistica> porDia(LocalDate mesReferencia);
	
}
