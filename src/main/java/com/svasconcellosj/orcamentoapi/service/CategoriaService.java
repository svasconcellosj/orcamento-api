package com.svasconcellosj.orcamentoapi.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svasconcellosj.orcamentoapi.model.CategoriaModel;
import com.svasconcellosj.orcamentoapi.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository cR;
	
	public List<CategoriaModel> buscaTodos() {
		return cR.findAll();
	}
	
	public CategoriaModel grava(CategoriaModel categoria) {
		return cR.save(categoria);
	}
	
	public CategoriaModel buscaId(Long id) {
		return cR.findById(id).orElse(null);
	}
	
	public void exclui(CategoriaModel categoria) {
		cR.delete(categoria);
	}
	
	public CategoriaModel altera(Long id, CategoriaModel categoria) {
		CategoriaModel cM = buscaId(id);
		BeanUtils.copyProperties(categoria, cM, "id");
		return grava(cM);
	}
	
}
