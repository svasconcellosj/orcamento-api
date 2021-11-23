package com.svasconcellosj.orcamentoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.svasconcellosj.orcamentoapi.model.LancamentoModel;

@Repository
public interface LancamentoRepository extends JpaRepository<LancamentoModel, Long> {

}
