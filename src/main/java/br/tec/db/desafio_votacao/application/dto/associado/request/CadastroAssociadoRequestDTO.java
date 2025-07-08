package br.tec.db.desafio_votacao.application.dto.associado.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Email;
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
public class CadastroAssociadoRequestDTO {
	
	private static final String MENSAGEM_CPF_INVALIDO = "deve conter apenas números e ter exatamente 11 dígitos";
	private static final String MENSAGEM_EMAIL_INVALIDO = "deve representar um e-mail valido";
	
	@NotBlank
	@Pattern(regexp = "^(\\d){11}$", message = MENSAGEM_CPF_INVALIDO)
	private String cpf;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	@Email(message = MENSAGEM_EMAIL_INVALIDO)
	private String email;
	
}
