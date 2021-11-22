package com.svasconcellosj.orcamentoapi.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.svasconcellosj.orcamentoapi.model.PessoaModel;
import com.svasconcellosj.orcamentoapi.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
	
	@Autowired
	private PessoaService pS;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<PessoaModel>> buscaPessoas() {
		List<PessoaModel> pM = pS.buscaTodos();
		return new ResponseEntity<List<PessoaModel>>(pM, HttpStatus.OK); 
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<PessoaModel> gravaPessoa(@RequestBody PessoaModel pessoa, HttpServletResponse response) {
		PessoaModel pM = pS.grava(pessoa);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(pM.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return new ResponseEntity<PessoaModel>(pM, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<PessoaModel> buscaPessoa(@PathVariable Long id) {
		PessoaModel pM = pS.buscaId(id);
		return new ResponseEntity<PessoaModel>(pM, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<PessoaModel> excluiPessoa(@PathVariable Long id) {
		PessoaModel pM = pS.buscaId(id);
		pS.exclui(pM);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<PessoaModel> alteraPessoa(@PathVariable Long id, @RequestBody PessoaModel pessoa) {
		PessoaModel pM = pS.altera(id, pessoa);
		return new ResponseEntity<PessoaModel>(pM, HttpStatus.OK);
	}
	
}
