package com.svasconcellosj.orcamentoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.svasconcellosj.orcamentoapi.model.LancamentoModel;
import com.svasconcellosj.orcamentoapi.repository.lancamento.LancamentoRepositoryQuery;

@Repository
public interface LancamentoRepository extends JpaRepository<LancamentoModel, Long>, LancamentoRepositoryQuery {

}
