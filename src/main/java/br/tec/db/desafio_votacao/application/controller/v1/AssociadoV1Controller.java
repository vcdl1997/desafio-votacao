package br.tec.db.desafio_votacao.application.controller.v1;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.tec.db.desafio_votacao.domain.entities.Associado;

@RestController
@RequestMapping(value = "/v1/associados")
public class AssociadoV1Controller {

	@GetMapping
	public List<Associado> listar(){
		return new ArrayList<>();
	}
	
	@GetMapping("/{idAssociado}")
	public Associado obterPorId(
		final @PathVariable Long idAssociado
	){
		return new Associado();
	}
	
	@PostMapping
	public Associado cadastrar(final @RequestBody Associado associado){
		return new Associado();
	}
	
	@PutMapping("/{idAssociado}")
	public Associado atualizar(
		final @PathVariable Long idAssociado,
		final @RequestBody Associado associado
	){
		return new Associado();
	}
	
	@DeleteMapping("/{idAssociado}")
	public Associado deletar(final @PathVariable Long idAssociado){
		return new Associado();
	}
}
