package com.svasconcellosj.orcamentoapi.categoria.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.svasconcellosj.orcamentoapi.categoria.dto.CategoriaListagem;
import com.svasconcellosj.orcamentoapi.categoria.model.CategoriaModel;
import com.svasconcellosj.orcamentoapi.categoria.repository.CategoriaRepository;
import com.svasconcellosj.orcamentoapi.categoria.repository.filter.CategoriaFilter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository cR;

	public Page<CategoriaModel> buscaTodos(CategoriaFilter categoriaFilter, Pageable pageable) {
		return cR.filtrar(categoriaFilter, pageable);
	}

	public CategoriaModel grava(CategoriaModel categoria) {
		return cR.save(categoria);
	}

	public CategoriaModel buscaId(Long id) {
		return cR.findById(id).orElse(null);
	}

	public void exclui(CategoriaModel categoria) {
		cR.delete(categoria);
	}

	public CategoriaModel altera(Long id, CategoriaModel categoria) {
		CategoriaModel cM = buscaId(id);
		BeanUtils.copyProperties(categoria, cM, "id");
		return grava(cM);
	}
	
	public byte[] relatorioListagemCategoria() throws JRException {
		List<CategoriaListagem> dados = cR.relatorioListagemCategoria();
		InputStream inputStream = this.getClass().getResourceAsStream("../report/ListagemCategoria.jasper");
		JasperPrint jasperPrint = JasperFillManager.fillReport( inputStream, null, new JRBeanCollectionDataSource(dados) );		
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}
	
	//ordenar de forma mais simples usando o JPArepository
	public byte[] findByOrderByDescricao() throws JRException {
		List<CategoriaModel> dados = cR.findByOrderByDescricao();
		InputStream inputStream = this.getClass().getResourceAsStream("../report/ListagemCategoria.jasper");
		JasperPrint jasperPrint = JasperFillManager.fillReport( inputStream, null, new JRBeanCollectionDataSource(dados) );		
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}
	
}
