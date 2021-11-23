package com.svasconcellosj.orcamentoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.svasconcellosj.orcamentoapi.model.PermissaoModel;

@Repository
public interface PermissaoRepository extends JpaRepository<PermissaoModel, Long> {

}
