package com.svasconcellosj.orcamentoapi.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.svasconcellosj.orcamentoapi.event.RecursoCriadoEvent;
import com.svasconcellosj.orcamentoapi.model.PessoaModel;
import com.svasconcellosj.orcamentoapi.repository.filter.PessoaFilter;
import com.svasconcellosj.orcamentoapi.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
	
	@Autowired
	private PessoaService pS;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and hasAuthority('SCOPE_read')" )
	public ResponseEntity<Page<PessoaModel>> buscaPessoas(PessoaFilter pessoaFilter, Pageable pageable) {
		Page<PessoaModel> pM = pS.buscaTodos(pessoaFilter,pageable);
		return new ResponseEntity<Page<PessoaModel>>(pM, HttpStatus.OK); 
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<PessoaModel> gravaPessoa(@Validated @RequestBody PessoaModel pessoa, HttpServletResponse response) {
		PessoaModel pM = pS.grava(pessoa);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pM.getId()));
		
		return new ResponseEntity<PessoaModel>(pM, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<PessoaModel> buscaPessoa(@PathVariable Long id) {
		PessoaModel pM = pS.buscaId(id);
		return pM == null ? new ResponseEntity<PessoaModel>(pM, HttpStatus.NOT_FOUND) : new ResponseEntity<PessoaModel>(pM, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<PessoaModel> excluiPessoa(@PathVariable Long id) {
		PessoaModel pM = pS.buscaId(id);
		pS.exclui(pM);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<PessoaModel> alteraPessoa(@Validated @PathVariable Long id, @RequestBody PessoaModel pessoa) {
		PessoaModel pM = pS.altera(id, pessoa);
		return new ResponseEntity<PessoaModel>(pM, HttpStatus.OK);
	}
	
}
