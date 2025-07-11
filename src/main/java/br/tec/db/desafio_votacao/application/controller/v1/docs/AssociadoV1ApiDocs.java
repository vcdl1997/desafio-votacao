package br.tec.db.desafio_votacao.application.controller.v1.docs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import br.tec.db.desafio_votacao.application.dto.associado.request.AtualizacaoAssociadoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.associado.request.CadastroAssociadoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.associado.request.FiltroAssociadoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.associado.response.AssociadoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Associados")
public interface AssociadoV1ApiDocs {

    @Operation(
        summary = "Listar Associados",
        description = "Retorna uma lista paginada de associados."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de associados retornada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AssociadoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Parâmetros de filtro inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
	@ResponseStatus(code = HttpStatus.OK)
    ResponseEntity<Page<AssociadoResponseDTO>> listar(final @Valid FiltroAssociadoRequestDTO dto, final Pageable pageable);

    @Operation(
        summary = "Obter associado por ID",
        description = "Busca os dados de um associado específico a partir do seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Associado encontrado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AssociadoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Associado não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
	@ResponseStatus(code = HttpStatus.OK)
    ResponseEntity<AssociadoResponseDTO> obterPorId(final @PathVariable(name = "id") Long id);

    @Operation(
        summary = "Cadastrar um novo associado",
        description = "Cria um novo associado no sistema e retorna seus dados cadastrados"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Associado criado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AssociadoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
	@ResponseStatus(code = HttpStatus.CREATED)
    ResponseEntity<AssociadoResponseDTO> cadastrar(
		final @Valid @RequestBody CadastroAssociadoRequestDTO dto, 
		final UriComponentsBuilder uriBuilder
	);

    @Operation(
        summary = "Atualizar dados de um associado",
        description = "Atualiza o nome e o e-mail de um associado existente com base no ID informado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Associado atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AssociadoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Associado não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<AssociadoResponseDTO> atualizar(
		final @PathVariable(name = "id") Long id, 
		final @Valid @RequestBody AtualizacaoAssociadoRequestDTO dto
	);

}
