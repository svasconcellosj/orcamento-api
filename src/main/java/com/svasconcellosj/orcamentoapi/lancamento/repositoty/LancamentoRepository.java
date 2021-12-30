package com.svasconcellosj.orcamentoapi.lancamento.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.svasconcellosj.orcamentoapi.lancamento.model.LancamentoModel;
import com.svasconcellosj.orcamentoapi.lancamento.repositoty.consult.LancamentoRepositoryQuery;

@Repository
public interface LancamentoRepository extends JpaRepository<LancamentoModel, Long>, LancamentoRepositoryQuery {

}
