package com.svasconcellosj.orcamentoapi.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.svasconcellosj.orcamentoapi.event.RecursoCriadoEvent;
import com.svasconcellosj.orcamentoapi.model.LancamentoModel;
import com.svasconcellosj.orcamentoapi.repository.filter.LancamentoFilter;
import com.svasconcellosj.orcamentoapi.service.LancamentoService;

@RestController
@RequestMapping("lancamentos")
public class LancamentoController {

	@Autowired
	private LancamentoService lS;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<LancamentoModel>> buscaLancamentos(LancamentoFilter lancamentoFilter, Pageable pageable) {
		Page<LancamentoModel> lM = lS.buscaTodos(lancamentoFilter, pageable);
		return new ResponseEntity<Page<LancamentoModel>>(lM, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<LancamentoModel> gravaLancamento(@Validated @RequestBody LancamentoModel lancamento, HttpServletResponse response) {
		LancamentoModel lM = lS.grava(lancamento);
		
		/*
		 * URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("{id}").
		 * buildAndExpand(lM.getId()).toUri(); response.addHeader("Location",
		 * uri.toASCIIString());
		 * 
		 * ou criando um Evento para encurtar o codigo (codigo a baixo)
		 */	
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lM.getId()));
		
		
		return new ResponseEntity<LancamentoModel>(lM, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<LancamentoModel> buscaLancamento(@PathVariable Long id) {
		LancamentoModel lM = lS.buscaId(id);
		return lM == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<LancamentoModel>(lM, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<LancamentoModel> excluiLancamento(@PathVariable Long id) {
		LancamentoModel lM = lS.buscaId(id);
		lS.exclui(lM);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<LancamentoModel> alteraLancamento(@Validated @PathVariable Long id, @RequestBody LancamentoModel lancamento) {
		LancamentoModel lM = lS.altera(id, lancamento);
		return new ResponseEntity<LancamentoModel>(lM, HttpStatus.OK);
	}
	
}
