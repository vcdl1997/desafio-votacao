package br.tec.db.desafio_votacao.application.controller.v1.docs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import br.tec.db.desafio_votacao.application.dto.pauta.request.CadastroPautaRequestDTO;
import br.tec.db.desafio_votacao.application.dto.pauta.request.FiltroPautaRequestDTO;
import br.tec.db.desafio_votacao.application.dto.pauta.response.PautaResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Pautas")
public interface PautaV1ApiDocs {

    @Operation(
        summary = "Listar pautas",
        description = "Retorna uma lista paginada de pautas, podendo ser filtrada por ID e/ou assunto"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de pautas retornada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = PautaResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Parâmetros de filtro inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Page<PautaResponseDTO>> listar(@Valid FiltroPautaRequestDTO dto, Pageable pageable);

    @Operation(
        summary = "Obter pauta por ID",
        description = "Retorna os dados de uma pauta específica com base no ID informado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pauta encontrada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = PautaResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Pauta não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<PautaResponseDTO> obterPorId(final @PathVariable(name = "id") Long id);

    @Operation(
        summary = "Cadastrar nova pauta",
        description = "Cria uma nova pauta de votação e retorna os dados da pauta cadastrada"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pauta criada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = PautaResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos enviados no corpo da requisição"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<PautaResponseDTO> cadastrar(
		final @Valid @RequestBody CadastroPautaRequestDTO dto,
		final UriComponentsBuilder uriBuilder
	);

}
