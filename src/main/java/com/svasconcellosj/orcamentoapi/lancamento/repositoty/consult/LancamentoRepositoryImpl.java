package com.svasconcellosj.orcamentoapi.lancamento.repositoty.consult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

import com.svasconcellosj.orcamentoapi.categoria.model.CategoriaModel_;
import com.svasconcellosj.orcamentoapi.lancamento.dto.LancamentoCategoriaEstatistica;
import com.svasconcellosj.orcamentoapi.lancamento.dto.LancamentoDiaEstatistica;
import com.svasconcellosj.orcamentoapi.lancamento.dto.LancamentoPessoaEstatistica;
import com.svasconcellosj.orcamentoapi.lancamento.model.LancamentoModel;
import com.svasconcellosj.orcamentoapi.lancamento.model.LancamentoModel_;
import com.svasconcellosj.orcamentoapi.lancamento.repositoty.filter.LancamentoFilter;
import com.svasconcellosj.orcamentoapi.lancamento.repositoty.projection.ResumoLancamento;
import com.svasconcellosj.orcamentoapi.pessoa.model.PessoaModel_;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	public Page<LancamentoModel> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoModel> criteria = builder.createQuery(LancamentoModel.class);
		Root<LancamentoModel> root = criteria.from(LancamentoModel.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<LancamentoModel> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}

	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,	Root<LancamentoModel> root) {
		List<Predicate> predicates = new ArrayList<>();

		//where descricao like '% campo %' 
		if(!ObjectUtils.isEmpty(lancamentoFilter.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get(LancamentoModel_.descricao)), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
		}
		
		//where DataVencimentoDe >= 
		if (lancamentoFilter.getDataVencimentoDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(LancamentoModel_.dataVencimento), lancamentoFilter.getDataVencimentoDe()));
		}
		
		//where DataVencimentoAte <=
		if (lancamentoFilter.getDataVencimentoAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(LancamentoModel_.dataVencimento), lancamentoFilter.getDataVencimentoAte()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	@Override
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
		Root<LancamentoModel> root = criteria.from(LancamentoModel.class);
		
		criteria.select(builder.construct(ResumoLancamento.class
				, root.get(LancamentoModel_.id), root.get(LancamentoModel_.descricao)
				, root.get(LancamentoModel_.dataVencimento), root.get(LancamentoModel_.dataPagamento)
				, root.get(LancamentoModel_.valor), root.get(LancamentoModel_.tipo)
				, root.get(LancamentoModel_.categoria).get(CategoriaModel_.descricao)
				, root.get(LancamentoModel_.pessoa).get(PessoaModel_.nome)));
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<ResumoLancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<LancamentoModel> root = criteria.from(LancamentoModel.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	@Override
	public List<LancamentoCategoriaEstatistica> porCategoria(LocalDate mesReferencia) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		//O que quer devolver
		CriteriaQuery<LancamentoCategoriaEstatistica> criteria = builder.createQuery(LancamentoCategoriaEstatistica.class);
		//Onde vai buscar os dados
		Root<LancamentoModel> root = criteria.from(LancamentoModel.class);
		
		//SELECT categoria, sum(valor) FROM lancamentos
		criteria.select(builder.construct(LancamentoCategoriaEstatistica.class, root.get(LancamentoModel_.categoria), builder.sum(root.get(LancamentoModel_.valor))));
		
		//WHERE dataVencimento >= primeiroDia AND davaVencimento <= ultimoDia
		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
		criteria.where(builder.greaterThanOrEqualTo(root.get(LancamentoModel_.dataVencimento), primeiroDia), builder.lessThanOrEqualTo(root.get(LancamentoModel_.dataVencimento), ultimoDia));
		
		//GROUP BY categoria
		criteria.groupBy(root.get(LancamentoModel_.categoria).get(CategoriaModel_.descricao));
		
		//ORDER BY categoria
		criteria.orderBy( builder.asc(root.get(LancamentoModel_.categoria).get(CategoriaModel_.descricao)) );
		
		//Monta a consulta
		TypedQuery<LancamentoCategoriaEstatistica> typedQuery = manager.createQuery(criteria);
		
		return typedQuery.getResultList();
	}
	
	@Override
	public List<LancamentoDiaEstatistica> porDia(LocalDate mesReferencia) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoDiaEstatistica> criteria = builder.createQuery(LancamentoDiaEstatistica.class);
		Root<LancamentoModel> root = criteria.from(LancamentoModel.class);
		
		//SELECT tipo, dataVencimento, sum(valor) FROM lancamentos
		criteria.select(builder.construct(LancamentoDiaEstatistica.class, root.get(LancamentoModel_.tipo),root.get(LancamentoModel_.dataVencimento) ,builder.sum(root.get(LancamentoModel_.valor))));		
		
		//WHERE dataVencimento >= primeiroDia AND davaVencimento <= ultimoDia
		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
		criteria.where(builder.greaterThanOrEqualTo(root.get(LancamentoModel_.dataVencimento), primeiroDia), builder.lessThanOrEqualTo(root.get(LancamentoModel_.dataVencimento), ultimoDia));
				
		//GROUP BY tipo, dataVencimento
		criteria.groupBy( root.get(LancamentoModel_.tipo), root.get(LancamentoModel_.dataVencimento) );
		
		//ORDER BY categoria
		criteria.orderBy( builder.asc(root.get(LancamentoModel_.tipo)) );
		
		//Monta a consulta
		TypedQuery<LancamentoDiaEstatistica> typedQuery = manager.createQuery(criteria);
		
		return typedQuery.getResultList();
	}
	
	@Override
	public List<LancamentoPessoaEstatistica> porPessoa(LocalDate inicio, LocalDate fim) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoPessoaEstatistica> criteria = builder.createQuery(LancamentoPessoaEstatistica.class);
		Root<LancamentoModel> root = criteria.from(LancamentoModel.class);
		
		//SELECT tipo, pessoa, sum(valor) FROM lancamentos
		criteria.select( builder.construct( LancamentoPessoaEstatistica.class, root.get(LancamentoModel_.tipo), root.get(LancamentoModel_.pessoa).get(PessoaModel_.nome), builder.sum(root.get(LancamentoModel_.valor)) ) );		
		
		//WHERE dataVencimento >= inicio AND davaVencimento <= fim
		criteria.where(builder.greaterThanOrEqualTo(root.get(LancamentoModel_.dataVencimento), inicio), builder.lessThanOrEqualTo(root.get(LancamentoModel_.dataVencimento), fim));
				
		//GROUP BY tipo, pessoa
		criteria.groupBy(root.get(LancamentoModel_.tipo), root.get(LancamentoModel_.pessoa).get(PessoaModel_.nome));
		
		//ORDER BY pessoa
		criteria.orderBy( builder.asc(root.get(LancamentoModel_.pessoa).get(PessoaModel_.nome)) );
		
		//Monta a consulta
		TypedQuery<LancamentoPessoaEstatistica> typedQuery = manager.createQuery(criteria);
		
		return typedQuery.getResultList();
	}
	
}
