package com.svasconcellosj.orcamentoapi.repository.catetoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.svasconcellosj.orcamentoapi.model.CategoriaModel;
import com.svasconcellosj.orcamentoapi.repository.filter.CategoriaFilter;

public interface CategoriaRepositoryQuery {

	public Page<CategoriaModel> filtrar(CategoriaFilter categoriaFilter, Pageable pageable);
	
}
