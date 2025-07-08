package br.tec.db.desafio_votacao.application.dto.associado.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AtualizacaoAssociadoRequestDTO {
	
	@NotBlank(message = "não deve estar em branco.")
	private String nome;
	
	@NotBlank(message = "não deve estar em branco.")
	@Email(message = "deve representar um e-mail valido.")
	private String email;
	
}
