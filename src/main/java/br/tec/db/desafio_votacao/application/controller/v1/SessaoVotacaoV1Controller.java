package br.tec.db.desafio_votacao.application.controller.v1;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.tec.db.desafio_votacao.application.dto.sessaovotacao.request.CadastroSessaoVotacaoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.sessaovotacao.request.FiltroSessaoVotacaoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.sessaovotacao.response.SessaoVotacaoResponseDTO;
import br.tec.db.desafio_votacao.application.usecases.sessaovotacao.CadastrarSessaoVotacaoUseCase;
import br.tec.db.desafio_votacao.application.usecases.sessaovotacao.ListarSessaoVotacaoPorIdPautaUseCase;
import br.tec.db.desafio_votacao.application.usecases.sessaovotacao.ObterSessaoVotacaoPorIdUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/sessoes-votacao")
@RequiredArgsConstructor
public class SessaoVotacaoV1Controller {
	
	private final ListarSessaoVotacaoPorIdPautaUseCase listarSessaoVotacaoPorId;
	private final ObterSessaoVotacaoPorIdUseCase obterSessaoVotacaoPorId;
	private final CadastrarSessaoVotacaoUseCase cadastrarSessaoVotacao;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Page<SessaoVotacaoResponseDTO>> listar(final @Valid FiltroSessaoVotacaoRequestDTO dto, final Pageable pageable){
		Page<SessaoVotacaoResponseDTO> response = listarSessaoVotacaoPorId.listar(dto, pageable);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<SessaoVotacaoResponseDTO> obterPorId(final @PathVariable Long id){
		SessaoVotacaoResponseDTO response = obterSessaoVotacaoPorId.executar(id);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<SessaoVotacaoResponseDTO> cadastrar(
		final @Valid @RequestBody CadastroSessaoVotacaoRequestDTO sessaoVotacao,
		final UriComponentsBuilder uriBuilder
	){
		SessaoVotacaoResponseDTO response = cadastrarSessaoVotacao.cadastrar(sessaoVotacao);
		URI uri = uriBuilder.path("/v1/sessoes-votacao/{id}").buildAndExpand(response.getId()).toUri();
		return ResponseEntity.created(uri).body(response);
	}
	
}
