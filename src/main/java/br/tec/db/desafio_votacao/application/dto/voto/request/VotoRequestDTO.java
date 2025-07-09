package br.tec.db.desafio_votacao.application.dto.voto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VotoRequestDTO {

	private static final String MENSAGEM_FORMATO_ID = "deve conter apenas números";
	private static final String MENSAGEM_FORMATO_RESPOSTA_VOTO = "deve conter apenas os valores 'SIM' ou 'NAO'";

	@NotBlank
	@Pattern(regexp = "^\\d*$", message = MENSAGEM_FORMATO_ID)
	private String idSessaoVotacao;
	
	@NotBlank
	@Pattern(regexp = "^\\d*$", message = MENSAGEM_FORMATO_ID)
	private String idAssociado;
	
	@NotBlank
	@Pattern(regexp = "^(SIM|NAO)$", message = MENSAGEM_FORMATO_RESPOSTA_VOTO)
	private String respostaVoto;
	
}
