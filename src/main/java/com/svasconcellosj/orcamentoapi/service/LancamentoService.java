package com.svasconcellosj.orcamentoapi.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svasconcellosj.orcamentoapi.model.LancamentoModel;
import com.svasconcellosj.orcamentoapi.repository.LancamentoRepository;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lR;
	
	public List<LancamentoModel> buscaTodos() {
		return lR.findAll();
	}
	
	public LancamentoModel grava(LancamentoModel lancamento) {
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
	
}
