package com.svasconcellosj.orcamentoapi.lancamento.service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.svasconcellosj.orcamentoapi.lancamento.dto.LancamentoCategoriaEstatistica;
import com.svasconcellosj.orcamentoapi.lancamento.dto.LancamentoDiaEstatistica;
import com.svasconcellosj.orcamentoapi.lancamento.dto.LancamentoPessoaEstatistica;
import com.svasconcellosj.orcamentoapi.lancamento.model.LancamentoModel;
import com.svasconcellosj.orcamentoapi.lancamento.repositoty.LancamentoRepository;
import com.svasconcellosj.orcamentoapi.lancamento.repositoty.filter.LancamentoFilter;
import com.svasconcellosj.orcamentoapi.pessoa.exception.PessoaInexistenteOuInativaException;
import com.svasconcellosj.orcamentoapi.pessoa.model.PessoaModel;
import com.svasconcellosj.orcamentoapi.pessoa.repository.PessoaRepository;
import com.svasconcellosj.orcamentoapi.pessoa.service.PessoaService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lR;
	
	@Autowired
	private PessoaRepository pR;
	
	@Autowired
	PessoaService pS;
	
	public Page<LancamentoModel> buscaTodos(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lR.filtrar(lancamentoFilter, pageable);
	}
	
	public LancamentoModel grava(LancamentoModel lancamento) {
		PessoaModel pM = pS.buscaId(lancamento.getPessoa().getId());
		if ( pM.isInativo() ) {
			return null;
		}
		return lR.save(lancamento);
	}
	
	public LancamentoModel buscaId(Long id) {
		return lR.findById(id).orElse(null);
	}
	
	public void exclui(LancamentoModel lancamento) {
		lR.delete(lancamento);
	}
	
	public LancamentoModel altera(Long id, LancamentoModel lancamento) {
		LancamentoModel lM = buscaId(id);
		BeanUtils.copyProperties(lancamento, lM, "id");
		return grava(lM);
	}
	
	public List<LancamentoCategoriaEstatistica> estatisticaPorCategoria(LocalDate data) {
		return lR.porCategoria(data);
	}
	
	public List<LancamentoDiaEstatistica> estatisticaPorDia(LocalDate data) {
		return lR.porDia(data);
	}
	
	public byte[] relatorioPorPessoa(LocalDate inicio, LocalDate fim) throws Exception {
		List<LancamentoPessoaEstatistica> dados = lR.porPessoa(inicio, fim);
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("DT_INICIO", Date.valueOf(inicio));
		parametros.put("DT_FIM", Date.valueOf(fim));
		parametros.put("REPORT_LOCALE", new Locale("pt","BR"));
		
		InputStream inputStream = this.getClass().getResourceAsStream("../report/lancamento/lancamentos-por-pessoa.jasper");
		
		JasperPrint jaspoerPrint = JasperFillManager.fillReport(inputStream, parametros, new JRBeanCollectionDataSource(dados));
		
		return JasperExportManager.exportReportToPdf(jaspoerPrint);		
	}
	
	public byte[] relatorioPorCategoria(LocalDate mesReferencia) throws Exception {
		List<LancamentoCategoriaEstatistica> dados = lR.porCategoria(mesReferencia);
		
		InputStream inputStream = this.getClass().getResourceAsStream("../report/lancamento/lancamento-por-categoria.jasper");
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("REPORT_LOCALE", new Locale("pt","BR"));
		
		JasperPrint jasperPrint = JasperFillManager.fillReport( inputStream, null, new JRBeanCollectionDataSource(dados) );
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}
	
	private void validarPessoa(LancamentoModel lancamento) {
		Optional<PessoaModel> pessoa = null;
		if (lancamento.getPessoa().getId() != null) {
			pessoa = pR.findById(lancamento.getPessoa().getId());
		}

		if (pessoa.isEmpty() || pessoa.get().isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
	}
	
}
