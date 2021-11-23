package com.svasconcellosj.orcamentoapi.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svasconcellosj.orcamentoapi.model.PermissaoModel;
import com.svasconcellosj.orcamentoapi.repository.PermissaoRepository;

@Service
public class PermissaoService {

	@Autowired
	private PermissaoRepository pR;
	
	public List<PermissaoModel> buscaTodos() {
		return pR.findAll();
	}
	
	public PermissaoModel grava(PermissaoModel permissao) {
		return pR.save(permissao);
	}
	
	public PermissaoModel buscaId(Long id) {
		return pR.findById(id).orElse(null);
	}
	
	public void excluir(PermissaoModel permissao) {
		pR.delete(permissao);
	}
	
	public PermissaoModel altera(Long id, PermissaoModel permissao) {
		PermissaoModel pM = buscaId(id);
		BeanUtils.copyProperties(permissao, pM, "id");
		return grava(pM);
	}
}
