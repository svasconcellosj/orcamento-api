package com.svasconcellosj.orcamentoapi.pessoa.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.svasconcellosj.orcamentoapi.pessoa.model.PessoaModel;
import com.svasconcellosj.orcamentoapi.pessoa.repository.PessoaRepository;
import com.svasconcellosj.orcamentoapi.pessoa.repository.filter.PessoaFilter;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pR;
	
	public Page<PessoaModel> buscaTodos(PessoaFilter pessoaFilter, Pageable pageable) {
		return pR.filtrar(pessoaFilter, pageable);
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
