package com.svasconcellosj.orcamentoapi.categoria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.svasconcellosj.orcamentoapi.categoria.model.CategoriaModel;
import com.svasconcellosj.orcamentoapi.categoria.repository.consult.CategoriaRepositoryQuery;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long>, CategoriaRepositoryQuery {

}
