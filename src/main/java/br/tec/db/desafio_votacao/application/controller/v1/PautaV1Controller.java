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

import br.tec.db.desafio_votacao.application.dto.pauta.request.CadastroPautaRequestDTO;
import br.tec.db.desafio_votacao.application.dto.pauta.request.FiltroPautaRequestDTO;
import br.tec.db.desafio_votacao.application.dto.pauta.response.PautaResponseDTO;
import br.tec.db.desafio_votacao.application.usecases.pauta.CadastrarPautaUseCase;
import br.tec.db.desafio_votacao.application.usecases.pauta.ListarPautasUseCase;
import br.tec.db.desafio_votacao.application.usecases.pauta.ObterPautaPorIdUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/pautas")
@RequiredArgsConstructor
public class PautaV1Controller {
	
	private final ListarPautasUseCase listarPautas;
	private final ObterPautaPorIdUseCase obterPautaPorId;
	private final CadastrarPautaUseCase cadastrarPauta;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Page<PautaResponseDTO>> listar(@Valid FiltroPautaRequestDTO dto, Pageable pageable){
		Page<PautaResponseDTO> resposta = listarPautas.executar(dto, pageable);
		return ResponseEntity.ok(resposta);
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<PautaResponseDTO> obterPorId(final @PathVariable Long id){
		PautaResponseDTO response = obterPautaPorId.executar(id);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<PautaResponseDTO> cadastrar(
		final @Valid @RequestBody CadastroPautaRequestDTO dto,
		final UriComponentsBuilder uriBuilder
	){
		PautaResponseDTO response = cadastrarPauta.executar(dto);
		URI uri = uriBuilder.path("/v1/pautas/{id}").buildAndExpand(response.getId()).toUri();
		return ResponseEntity.created(uri).body(response);
	}
	
}
