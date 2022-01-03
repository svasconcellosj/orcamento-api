package com.svasconcellosj.orcamentoapi.categoria.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.svasconcellosj.orcamentoapi.categoria.model.CategoriaModel;
import com.svasconcellosj.orcamentoapi.categoria.repository.filter.CategoriaFilter;
import com.svasconcellosj.orcamentoapi.categoria.service.CategoriaService;
import com.svasconcellosj.orcamentoapi.event.RecursoCriadoEvent;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService cS;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and hasAuthority('SCOPE_read')" )
	public ResponseEntity<Page<CategoriaModel>> buscaCategorias(CategoriaFilter categoriaFilter, Pageable pageable) {
		Page<CategoriaModel> cM = cS.buscaTodos(categoriaFilter, pageable);
		return new ResponseEntity<Page<CategoriaModel>>(cM, HttpStatus.OK);		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and hasAuthority('SCOPE_write')")
	public ResponseEntity<CategoriaModel> gravaCategoria(@Validated @RequestBody CategoriaModel categoria, HttpServletResponse response) {
		CategoriaModel cM = cS.grava(categoria);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, cM.getId()));
	 
		return new ResponseEntity<CategoriaModel>(cM, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and hasAuthority('SCOPE_read')" )
	public ResponseEntity<CategoriaModel> buscaCategoria(@PathVariable Long id) {
		CategoriaModel cM = cS.buscaId(id);
		return cM == null ? new ResponseEntity<CategoriaModel>(cM, HttpStatus.NOT_FOUND) : new ResponseEntity<CategoriaModel>(cM, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	@PreAuthorize("hasAuthority('ROLE_REMOVER_CATEGORIA') and hasAuthority('SCOPE_write')")
	public ResponseEntity<CategoriaModel> excluiCategoria(@PathVariable Long id) {
		CategoriaModel cM = cS.buscaId(id);
		cS.exclui(cM);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and hasAuthority('SCOPE_write')")
	public ResponseEntity<CategoriaModel> alteraCategoria(@Validated @PathVariable Long id, @RequestBody CategoriaModel categoria) {
		CategoriaModel cM = cS.altera(id, categoria);
		return new ResponseEntity<CategoriaModel>(cM, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "listagem-categorias")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and hasAuthority('SCOPE_write')")
	public ResponseEntity<byte[]> relatorioListagemCategoria() throws JRException {
		byte[] listagem = cS.relatorioListagemCategoria();
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(listagem);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "listagem-categoriasDescricao")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and hasAuthority('SCOPE_write')")
	public ResponseEntity<byte[]> relatorioListagemCategoriaDescricao() throws JRException {
		byte[] listagem = cS.findByOrderByDescricao();
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(listagem);
	}
	
}
