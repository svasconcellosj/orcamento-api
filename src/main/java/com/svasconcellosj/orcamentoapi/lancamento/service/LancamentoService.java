package com.svasconcellosj.orcamentoapi.lancamento.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.svasconcellosj.orcamentoapi.lancamento.dto.LancamentoCategoriaEstatistica;
import com.svasconcellosj.orcamentoapi.lancamento.dto.LancamentoDiaEstatistica;
import com.svasconcellosj.orcamentoapi.lancamento.model.LancamentoModel;
import com.svasconcellosj.orcamentoapi.lancamento.repositoty.LancamentoRepository;
import com.svasconcellosj.orcamentoapi.lancamento.repositoty.filter.LancamentoFilter;
import com.svasconcellosj.orcamentoapi.pessoa.model.PessoaModel;
import com.svasconcellosj.orcamentoapi.pessoa.service.PessoaService;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lR;
	
	@Autowired
	PessoaService pS;
	
	public Page<LancamentoModel> buscaTodos(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lR.filtrar(lancamentoFilter, pageable);
	}
	
	public LancamentoModel grava(LancamentoModel lancamento) {
		PessoaModel pM = pS.buscaId(lancamento.getPessoa().getId());
		if ( pM.isInativo() ) {
			return null;
		}
		return lR.save(lancamento);
	}
	
	public LancamentoModel buscaId(Long id) {
		return lR.findById(id).orElse(null);
	}
	
	public void exclui(LancamentoModel lancamento) {
		lR.delete(lancamento);
	}
	
	public LancamentoModel altera(Long id, LancamentoModel lancamento) {
		LancamentoModel lM = buscaId(id);
		BeanUtils.copyProperties(lancamento, lM, "id");
		return grava(lM);
	}
	
	public List<LancamentoCategoriaEstatistica> estatisticaPorCategoria(LocalDate data) {
		return lR.porCategoria(data);
	}
	
	public List<LancamentoDiaEstatistica> estatisticaPorDia(LocalDate data) {
		return lR.porDia(data);
	}
	
}
