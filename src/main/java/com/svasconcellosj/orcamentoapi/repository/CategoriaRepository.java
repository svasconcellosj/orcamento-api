package com.svasconcellosj.orcamentoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.svasconcellosj.orcamentoapi.model.CategoriaModel;
import com.svasconcellosj.orcamentoapi.repository.catetoria.CategoriaRepositoryQuery;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long>, CategoriaRepositoryQuery {

}
