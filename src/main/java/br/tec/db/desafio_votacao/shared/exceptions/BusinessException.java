package br.tec.db.desafio_votacao.shared.exceptions;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.tec.db.desafio_votacao.application.dto.commons.response.ResponseErrorDTO;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BusinessException(String mensagem) {
		super(mensagem);
	}@ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseErrorDTO> handleNotFoundException(NotFoundException ex) {
        ResponseErrorDTO resposta = ResponseErrorDTO.builder()
        	.mensagem(ex.getMessage())
        	.traceId(UUID.randomUUID())
        	.timestamp(LocalDateTime.now())
        	.build();
		
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }
	
}
