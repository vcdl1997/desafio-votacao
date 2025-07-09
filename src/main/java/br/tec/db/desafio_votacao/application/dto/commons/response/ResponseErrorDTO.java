package br.tec.db.desafio_votacao.application.dto.commons.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@JsonInclude(value = Include.NON_NULL)
public class ResponseErrorDTO {

	private String campo;
	
	private String mensagem;
	
	private UUID traceId;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="GMT-3")
	private LocalDateTime timestamp;
	
	private List<ResponseErrorDTO> erros;
	
	public static List<ResponseErrorDTO> criar(final List<FieldError> errors){
		if(Objects.isNull(errors) || errors.isEmpty()) {
			throw new IllegalArgumentException("Lista de erros inválida: nula ou vazia.");
		}
		
		return errors.stream().map(error -> ResponseErrorDTO.criar(error)).collect(Collectors.toList());
	}
	
	public static ResponseErrorDTO criar(final FieldError error){
		Objects.requireNonNull(error, "FieldError não pode ser nulo");
		
		return ResponseErrorDTO
			.builder()
			.campo(error.getField())
			.mensagem(error.getDefaultMessage())
			.build()
		;
	}
	
}
