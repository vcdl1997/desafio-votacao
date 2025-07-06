package br.tec.db.desafio_votacao.application.controller.v1;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;

@RestController
@RequestMapping("/v1/pautas")
public class SessaoVotacaoV1Controller {

	@GetMapping("/{idPauta}/sessoes-votacao")
	public List<SessaoVotacao> listarPorIdPauta(final @PathVariable Long idPauta){
		return new ArrayList<>();
	}
	
	@PostMapping("/{idPauta}/sessoes-votacao")
	public SessaoVotacao cadastrar(
		final @PathVariable Long idPauta, 
		final @RequestBody SessaoVotacao sessaoVotacao
	){
		return new SessaoVotacao();
	}
	
}
