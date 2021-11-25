package com.svasconcellosj.orcamentoapi.event.listiner;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.svasconcellosj.orcamentoapi.event.RecursoCriadoEvent;

@Component
public class RecursoCriadoListiner implements ApplicationListener<RecursoCriadoEvent> {

	public void onApplicationEvent(RecursoCriadoEvent recursoCriadoEvent) {

		HttpServletResponse response = recursoCriadoEvent.getResponse();
		Long id = recursoCriadoEvent.getId();

		adicionarHeaderLocation(response, id);
	}

	private void adicionarHeaderLocation(HttpServletResponse response, Long id) {

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
		response.setHeader("Location", uri.toASCIIString());

	}

}
