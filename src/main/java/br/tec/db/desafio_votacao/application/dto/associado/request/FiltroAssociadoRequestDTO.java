package br.tec.db.desafio_votacao.application.dto.associado.request;

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
public class FiltroAssociadoRequestDTO {
	
	@Pattern(regexp = "^\\d*$", message = "O valor deve ser um número ou nulo.")
	private String id;
	
	@Pattern(regexp = "^\\d{11}$|^$", message = "O valor deve ser um número de 11 dígitos ou vazio.")
	private String cpf;
	
	private String nome;
	
	private String email;
	
}
