package br.tec.db.desafio_votacao.application.dto.pauta.request;

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
public class FiltroPautaRequestDTO {

	@Pattern(regexp = "^\\d*$", message = "O valor deve ser um número ou nulo.")
	private String id;
	
	private String assunto;
	
}
