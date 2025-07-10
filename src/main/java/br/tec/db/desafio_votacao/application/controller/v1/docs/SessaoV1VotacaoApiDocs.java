package br.tec.db.desafio_votacao.application.controller.v1.docs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import br.tec.db.desafio_votacao.application.dto.sessaovotacao.request.CadastroSessaoVotacaoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.sessaovotacao.request.FiltroSessaoVotacaoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.sessaovotacao.response.SessaoVotacaoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Sessões de Votação")
public interface SessaoV1VotacaoApiDocs {

    @Operation(
        summary = "Listar sessões de votação",
        description = "Retorna uma lista paginada de sessões de votação, podendo ser filtrada por ID da sessão ou ID da pauta"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de sessões retornada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = SessaoVotacaoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Parâmetros de filtro inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
	@ResponseStatus(HttpStatus.OK)
    ResponseEntity<Page<SessaoVotacaoResponseDTO>> listar(final @Valid FiltroSessaoVotacaoRequestDTO dto, final Pageable pageable);

    @Operation(
        summary = "Obter sessão de votação por ID",
        description = "Retorna os dados de uma sessão de votação específica com base no ID informado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sessão de votação encontrada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = SessaoVotacaoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Sessão de votação não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
	@ResponseStatus(HttpStatus.OK)
    ResponseEntity<SessaoVotacaoResponseDTO> obterPorId(final @PathVariable(name = "id") Long id);

    @Operation(
        summary = "Cadastrar nova sessão de votação",
        description = "Cria uma nova sessão de votação para uma pauta existente. A sessão pode ter um horário de encerramento definido ou usar o valor padrão (1 minuto)."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Sessão de votação criada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = SessaoVotacaoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos enviados no corpo da requisição"),
        @ApiResponse(responseCode = "404", description = "Pauta não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<SessaoVotacaoResponseDTO> cadastrar(
		final @Valid @RequestBody CadastroSessaoVotacaoRequestDTO sessaoVotacao,
		final UriComponentsBuilder uriBuilder
	);
}
