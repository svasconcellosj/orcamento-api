package com.svasconcellosj.orcamentoapi.categoria.repository.consult;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.svasconcellosj.orcamentoapi.categoria.dto.CategoriaListagem;
import com.svasconcellosj.orcamentoapi.categoria.model.CategoriaModel;
import com.svasconcellosj.orcamentoapi.categoria.repository.filter.CategoriaFilter;

public interface CategoriaRepositoryQuery {

	public Page<CategoriaModel> filtrar(CategoriaFilter categoriaFilter, Pageable pageable);
	
	public List<CategoriaListagem> relatorioListagemCategoria();
	
}
