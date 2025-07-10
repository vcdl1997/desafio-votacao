package br.tec.db.desafio_votacao.application.controller.v1.docs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import br.tec.db.desafio_votacao.application.dto.voto.request.FiltroVotoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.voto.request.VotoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.voto.response.VotoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Votos")
public interface VotoV1ApiDocs {

    @Operation(
        summary = "Registrar um voto",
        description = "Registra o voto de um associado em uma sessão de votação. O voto pode ser 'SIM' ou 'NAO'."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Voto registrado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = VotoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos enviados no corpo da requisição"),
        @ApiResponse(responseCode = "404", description = "Sessão ou associado não encontrado"),
        @ApiResponse(responseCode = "422", description = "Voto não permitido ou sessão encerrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
	@ResponseStatus(value = HttpStatus.CREATED)
    ResponseEntity<VotoResponseDTO> cadastrar(final @Valid @RequestBody VotoRequestDTO dto, final UriComponentsBuilder uriBuilder);

    @Operation(
        summary = "Obter resultado da votação",
        description = "Retorna o resultado da votação de uma sessão específica, com a quantidade de votos por resposta."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resultado obtido com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = VotoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
        @ApiResponse(responseCode = "404", description = "Sessão de votação não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
	@ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<VotoResponseDTO> obterResultadoVotacao(@Valid FiltroVotoRequestDTO dto);

}
