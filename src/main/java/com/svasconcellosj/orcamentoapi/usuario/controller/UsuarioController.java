package com.svasconcellosj.orcamentoapi.usuario.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.svasconcellosj.orcamentoapi.event.RecursoCriadoEvent;
import com.svasconcellosj.orcamentoapi.usuario.model.UsuarioModel;
import com.svasconcellosj.orcamentoapi.usuario.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService uS;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UsuarioModel>> buscaUsuarios() {
		List<UsuarioModel> uM = uS.bustaTodos();
		return new ResponseEntity<List<UsuarioModel>>(uM, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<UsuarioModel> gravaUsuario(@Validated @RequestBody UsuarioModel usuario, HttpServletResponse response) {
		UsuarioModel uM = uS.grava(usuario);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, uM.getId()));
		
		return new ResponseEntity<UsuarioModel>(uM, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<UsuarioModel> buscaUsuario(@PathVariable Long id) {
		UsuarioModel uM = uS.buscaId(id);
		return uM == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<UsuarioModel>(uM, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<UsuarioModel> excluiUsuario(@PathVariable Long id) {
		UsuarioModel uM = uS.buscaId(id);
		uS.exclui(uM);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<UsuarioModel> alteraUsuario(@Validated @PathVariable Long id, @RequestBody UsuarioModel usuario) {
		UsuarioModel uM = uS.altera(id, usuario);
		return new ResponseEntity<UsuarioModel>(uM, HttpStatus.OK);
	}

}
