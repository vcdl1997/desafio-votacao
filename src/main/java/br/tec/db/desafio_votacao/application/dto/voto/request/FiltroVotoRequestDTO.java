package br.tec.db.desafio_votacao.application.dto.voto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FiltroVotoRequestDTO {

	private static final String MENSAGEM_FORMATO_ID = "deve conter apenas números";
	
	@NotBlank
	@Pattern(regexp = "^\\d*$", message = MENSAGEM_FORMATO_ID)
	private String idSessaoVotacao;
	
}
