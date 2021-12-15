package com.svasconcellosj.orcamentoapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.svasconcellosj.orcamentoapi.model.CategoriaModel;
import com.svasconcellosj.orcamentoapi.repository.CategoriaRepository;
import com.svasconcellosj.orcamentoapi.repository.filter.CategoriaFilter;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository cR;

	public Page<CategoriaModel> buscaTodos(CategoriaFilter categoriaFilter, Pageable pageable) {
		return cR.filtrar(categoriaFilter, pageable);
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
