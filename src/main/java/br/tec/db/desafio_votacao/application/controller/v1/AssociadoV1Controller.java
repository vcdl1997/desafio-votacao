package br.tec.db.desafio_votacao.application.controller.v1;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.tec.db.desafio_votacao.application.controller.v1.docs.AssociadoV1ApiDocs;
import br.tec.db.desafio_votacao.application.dto.associado.request.AtualizacaoAssociadoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.associado.request.CadastroAssociadoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.associado.request.FiltroAssociadoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.associado.response.AssociadoResponseDTO;
import br.tec.db.desafio_votacao.application.usecases.associado.AtualizarAssociadoUseCase;
import br.tec.db.desafio_votacao.application.usecases.associado.CadastrarAssociadoUseCase;
import br.tec.db.desafio_votacao.application.usecases.associado.ListarAssociadosUseCase;
import br.tec.db.desafio_votacao.application.usecases.associado.ObterAssociadoPorIdUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/associados")
@RequiredArgsConstructor
public class AssociadoV1Controller implements AssociadoV1ApiDocs {
	
	private final ListarAssociadosUseCase listarAssociados;
	private final ObterAssociadoPorIdUseCase obterAssociadoPorId;
	private final CadastrarAssociadoUseCase cadastrarAssociado;
	private final AtualizarAssociadoUseCase atualizarAssociado;

	@GetMapping
	public ResponseEntity<Page<AssociadoResponseDTO>> listar(final @Valid FiltroAssociadoRequestDTO dto, final Pageable pageable){
		Page<AssociadoResponseDTO> response = listarAssociados.executar(dto, pageable);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AssociadoResponseDTO> obterPorId(final @PathVariable Long id){
		AssociadoResponseDTO response = obterAssociadoPorId.executar(id);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping
	public ResponseEntity<AssociadoResponseDTO> cadastrar(
		final @Valid @RequestBody CadastroAssociadoRequestDTO dto, 
		final UriComponentsBuilder uriBuilder
	){
		AssociadoResponseDTO response = cadastrarAssociado.executar(dto);
		URI uri = uriBuilder.path("/v1/associados/{id}").buildAndExpand(response.getId()).toUri();     
		return ResponseEntity.created(uri).body(response);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<AssociadoResponseDTO> atualizar(
		final @PathVariable(name = "id") Long id, 
		final @Valid @RequestBody AtualizacaoAssociadoRequestDTO dto
	) {
		AssociadoResponseDTO response = atualizarAssociado.executar(id, dto);
		return ResponseEntity.ok(response);
	}
}
