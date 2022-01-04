package com.svasconcellosj.orcamentoapi.pessoa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.svasconcellosj.orcamentoapi.pessoa.model.PessoaModel;
import com.svasconcellosj.orcamentoapi.pessoa.repository.consult.PessoaRepositoryQuery;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaModel, Long>, PessoaRepositoryQuery {

}
