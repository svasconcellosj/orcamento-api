package com.svasconcellosj.orcamentoapi.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svasconcellosj.orcamentoapi.model.UsuarioModel;
import com.svasconcellosj.orcamentoapi.repository.UsuarioRepositoy;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepositoy uR;
	
	public List<UsuarioModel> bustaTodos() {
		return uR.findAll();
	}
	
	public UsuarioModel grava(UsuarioModel usuario) {
		return uR.save(usuario);
	}
	
	public UsuarioModel buscaId(Long id) {
		return uR.findById(id).orElse(null);
	}
	
	public void exclui(UsuarioModel usuario) {
		uR.delete(usuario);
	}
	
	public UsuarioModel altera(Long id, UsuarioModel usuario) {
		UsuarioModel uM = buscaId(id);
		BeanUtils.copyProperties(usuario, uM, "id");
		return grava(uM);
	}
	
}
