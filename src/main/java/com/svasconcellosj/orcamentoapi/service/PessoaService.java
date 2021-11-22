package com.svasconcellosj.orcamentoapi.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svasconcellosj.orcamentoapi.model.PessoaModel;
import com.svasconcellosj.orcamentoapi.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pR;
	
	public List<PessoaModel> buscaTodos() {
		return pR.findAll();
	}
	
	public PessoaModel grava(PessoaModel pessoa) {
		return pR.save(pessoa);
	}
	
	public PessoaModel buscaId(Long id) {
		return pR.findById(id).orElse(null);
	}
	
	public void exclui(PessoaModel pessoa) {
		pR.delete(pessoa);
	}
	
	public PessoaModel altera(Long id, PessoaModel pessoa) {
		PessoaModel pM = buscaId(id);
		
		BeanUtils.copyProperties(pessoa, pM, "id");
		return grava(pessoa); 		
	}
}
