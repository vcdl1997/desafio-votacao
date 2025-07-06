package br.tec.db.desafio_votacao.application.controller.v1;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.tec.db.desafio_votacao.domain.entities.Pauta;

@RestController
@RequestMapping("/v1/pautas")
public class PautaV1Controller {

	@GetMapping
	public List<Pauta> listar(){
		return new ArrayList<>();
	}
	
	@PostMapping
	public Pauta cadastrar(final @RequestBody Pauta pauta){
		return new Pauta();
	}
	
}
