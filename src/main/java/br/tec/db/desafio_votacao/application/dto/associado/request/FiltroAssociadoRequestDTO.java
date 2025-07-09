package br.tec.db.desafio_votacao.application.dto.associado.request;

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
public class FiltroAssociadoRequestDTO {

	private static final String REGEX_PERMITE_APENAS_NUMEROS = "^\\d*$";
	private static final String REGEX_PERMITE_APENAS_NUMERO_COM_11_DIGITOS_OU_VAZIO = "^(?:\\d{11})?$";
	private static final String MENSAGEM_FILTRO_ID = "deve ser um número ou nulo";
	private static final String MENSAGEM_FILTRO_CPF = "deve ser um número de 11 dígitos ou vazio";
	
	@Pattern(regexp = REGEX_PERMITE_APENAS_NUMEROS, message = MENSAGEM_FILTRO_ID)
	private String id;
	
	@Pattern(regexp = REGEX_PERMITE_APENAS_NUMERO_COM_11_DIGITOS_OU_VAZIO, message = MENSAGEM_FILTRO_CPF)
	private String cpf;
	
	private String nome;
	
	private String email;
	
}
