package br.tec.db.desafio_votacao.application.controller.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.tec.db.desafio_votacao.application.dto.voto.request.FiltroVotoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.voto.request.VotoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.voto.response.VotoResponseDTO;
import br.tec.db.desafio_votacao.application.usecases.voto.CadastrarVotoUseCase;
import br.tec.db.desafio_votacao.application.usecases.voto.ObterResultadoVotacaoUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/votos")
@RequiredArgsConstructor
public class VotoV1Controller {
	
	private final CadastrarVotoUseCase cadastrarVoto;
	private final ObterResultadoVotacaoUseCase obterResultadoVotacao;

	@PostMapping
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<VotoResponseDTO> cadastrar(
		final @Valid @RequestBody VotoRequestDTO dto,
		final UriComponentsBuilder uriBuilder
	){
		VotoResponseDTO response = cadastrarVoto.executar(dto);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<VotoResponseDTO> obterResultadoVotacao(@Valid FiltroVotoRequestDTO dto){
		VotoResponseDTO response = obterResultadoVotacao.executar(dto);
		return ResponseEntity.ok(response);
	}
	
}
