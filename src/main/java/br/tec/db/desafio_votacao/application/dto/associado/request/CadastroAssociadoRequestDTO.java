package br.tec.db.desafio_votacao.application.dto.associado.request;

import jakarta.validation.constraints.Email;
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
public class CadastroAssociadoRequestDTO {
	
	@NotBlank(message = "O valor não deve estar em branco.")
	@Pattern(regexp = "^(\\d){11}$", message = "O valor deve conter apenas números e ter exatamente 11 dígitos.")
	private String cpf;
	
	@NotBlank(message = "O valor não deve estar em branco.")
	private String nome;
	
	@NotBlank(message = "O valor não deve estar em branco.")
	@Email(message = "O valor deve representar um e-mail valido.")
	private String email;
	
}
