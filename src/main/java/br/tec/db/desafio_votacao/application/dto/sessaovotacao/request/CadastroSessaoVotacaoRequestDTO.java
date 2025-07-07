package br.tec.db.desafio_votacao.application.dto.sessaovotacao.request;

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
public class CadastroSessaoVotacaoRequestDTO {

	@NotBlank
	@Pattern(regexp = "^(\\d)$")
	private String idPauta;
	
	@NotBlank
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$")
	private String dataHoraInicio;
	
	@NotBlank
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$")
	private String dataHoraEncerramento;
	
}
