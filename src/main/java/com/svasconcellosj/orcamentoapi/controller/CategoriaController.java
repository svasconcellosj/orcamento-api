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

import com.svasconcellosj.orcamentoapi.model.CategoriaModel;
import com.svasconcellosj.orcamentoapi.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService cS;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaModel>> buscaCategorias() {
		List<CategoriaModel> cM = cS.buscaTodos();
		return new ResponseEntity<List<CategoriaModel>>(cM, HttpStatus.OK);		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CategoriaModel> gravaCategoria(@Validated @RequestBody CategoriaModel categoria, HttpServletResponse response) {
		CategoriaModel cM = cS.grava(categoria);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(cM.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
	 
		return new ResponseEntity<CategoriaModel>(cM, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<CategoriaModel> buscaCategoria(@PathVariable Long id) {
		CategoriaModel cM = cS.buscaId(id);
		return cM == null ? new ResponseEntity<CategoriaModel>(cM, HttpStatus.NOT_FOUND) : new ResponseEntity<CategoriaModel>(cM, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<CategoriaModel> excluiCategoria(@PathVariable Long id) {
		CategoriaModel cM = cS.buscaId(id);
		cS.exclui(cM);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<CategoriaModel> alteraCategoria(@Validated @PathVariable Long id, @RequestBody CategoriaModel categoria) {
		CategoriaModel cM = cS.altera(id, categoria);
		return new ResponseEntity<CategoriaModel>(cM, HttpStatus.OK);
	}
	
}
