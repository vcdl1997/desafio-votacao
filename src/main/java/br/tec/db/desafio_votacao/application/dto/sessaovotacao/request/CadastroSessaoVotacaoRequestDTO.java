package br.tec.db.desafio_votacao.application.dto.sessaovotacao.request;

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
public class CadastroSessaoVotacaoRequestDTO {
	
	private static final String MENSAGEM_FORMATO_ID = "deve conter apenas números";
	private static final String MENSAGEM_FORMATO_DATA_HORA = "deve estar no formato yyyy-MM-dd HH:mm (exemplo: 2025-07-07 21:00)";

	@NotBlank
	@Pattern(regexp = "^\\d*$", message = MENSAGEM_FORMATO_ID)
	private String idPauta;
	
	@Pattern(regexp = "^(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}|)$", message = MENSAGEM_FORMATO_DATA_HORA)
	private String dataHoraEncerramento;
	
}
