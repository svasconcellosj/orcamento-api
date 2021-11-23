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

import com.svasconcellosj.orcamentoapi.model.PermissaoModel;
import com.svasconcellosj.orcamentoapi.service.PermissaoService;

@RestController
@RequestMapping("/permissoes")
public class PermissaoController {

	@Autowired
	private PermissaoService pS;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<PermissaoModel>> buscaPermissoes() {
		List<PermissaoModel> pM = pS.buscaTodos();
		return new ResponseEntity<List<PermissaoModel>>(pM, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<PermissaoModel> gravaPermissao(@Validated @RequestBody PermissaoModel permissao, HttpServletResponse response) {
		PermissaoModel pM = pS.grava(permissao);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(pM.getId()).toUri();
		response.addHeader("Location", uri.toASCIIString());

		return new ResponseEntity<PermissaoModel>(pM, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<PermissaoModel> buscaPermissao(@PathVariable Long id) {
		PermissaoModel pM = pS.buscaId(id);
		return pM == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<PermissaoModel>(pM, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<PermissaoModel> excluiPermissao(@PathVariable Long id) {
		PermissaoModel pM = pS.buscaId(id);
		pS.excluir(pM);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<PermissaoModel> alteraPermissao(@Validated @PathVariable Long id,	@RequestBody PermissaoModel permissao) {
		PermissaoModel pM = pS.altera(id, permissao);
		return new ResponseEntity<PermissaoModel>(pM, HttpStatus.OK);
	}

}
