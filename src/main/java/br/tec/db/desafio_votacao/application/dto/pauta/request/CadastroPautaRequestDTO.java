package br.tec.db.desafio_votacao.application.dto.pauta.request;

import jakarta.validation.constraints.NotBlank;
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
public class CadastroPautaRequestDTO {

	@NotBlank
	private String assunto;
	
}
