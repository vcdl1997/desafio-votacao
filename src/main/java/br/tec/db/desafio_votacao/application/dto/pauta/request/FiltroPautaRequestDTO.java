package br.tec.db.desafio_votacao.application.dto.pauta.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class FiltroPautaRequestDTO {
	
	private static final String MENSAGEM_FILTRO_ID = "deve ser um número ou nulo";

	@Pattern(regexp = "^\\d*$", message = MENSAGEM_FILTRO_ID)
	private String id;
	
	private String assunto;
	
}
