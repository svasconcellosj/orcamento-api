package com.svasconcellosj.orcamentoapi.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.svasconcellosj.orcamentoapi.usuario.model.PermissaoModel;

@Repository
public interface PermissaoRepository extends JpaRepository<PermissaoModel, Long> {

}
