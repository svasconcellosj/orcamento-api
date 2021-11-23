package com.svasconcellosj.orcamentoapi.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.svasconcellosj.orcamentoapi.model.LancamentoModel;
import com.svasconcellosj.orcamentoapi.service.LancamentoService;

@RestController
@RequestMapping("lancamentos")
public class LancamentoController {

	@Autowired
	private LancamentoService lS;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<LancamentoModel>> buscaLancamentos() {
		List<LancamentoModel> lM = lS.buscaTodos();
		return new ResponseEntity<List<LancamentoModel>>(lM, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<LancamentoModel> gravaLancamento(@Validated @RequestBody LancamentoModel lancamento, HttpServletResponse response) {
		LancamentoModel lM = lS.grava(lancamento);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("{id}").buildAndExpand(lM.getId()).toUri();
		response.addHeader("Location", uri.toASCIIString());
		
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
