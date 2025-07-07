package br.tec.db.desafio_votacao.application.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.tec.db.desafio_votacao.domain.entities.Voto;

@RestController
@RequestMapping("/v1/votacao")
public class VotacaoV1Controller {

	@PostMapping
	public Voto computarVoto(
		final @RequestBody Voto votacao
	){
		return new Voto();
	}
	
	@GetMapping
	public Voto obterResultadoPorIdSessaoVotacao(@RequestParam Long idSessaoVotacao){
		return new Voto();
	}
	
}
