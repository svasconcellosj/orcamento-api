package com.svasconcellosj.orcamentoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.svasconcellosj.orcamentoapi.model.UsuarioModel;

@Repository
public interface UsuarioRepositoy extends JpaRepository<UsuarioModel, Long>{

}
