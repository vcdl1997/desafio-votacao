package br.tec.db.desafio_votacao.application.controller.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.tec.db.desafio_votacao.application.dto.commons.response.ResponseErrorDTO;
import br.tec.db.desafio_votacao.shared.exceptions.BusinessException;
import br.tec.db.desafio_votacao.shared.exceptions.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
        List<ResponseErrorDTO> erros = ResponseErrorDTO.criar(result.getFieldErrors());
        ResponseErrorDTO resposta = ResponseErrorDTO.builder()
        	.erros(erros)
        	.traceId(UUID.randomUUID())
        	.timestamp(LocalDateTime.now())
        	.build();
		
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }
	
	@ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseErrorDTO> handleNotFoundException(NotFoundException ex) {
        ResponseErrorDTO resposta = ResponseErrorDTO.builder()
        	.mensagem(ex.getMessage())
        	.traceId(UUID.randomUUID())
        	.timestamp(LocalDateTime.now())
        	.build();
		
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }
	
	@ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseErrorDTO> handleBusinessException(BusinessException ex) {
        ResponseErrorDTO resposta = ResponseErrorDTO.builder()
        	.mensagem(ex.getMessage())
        	.traceId(UUID.randomUUID())
        	.timestamp(LocalDateTime.now())
        	.build();
		
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(resposta);
    }
	
	@ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseErrorDTO> handleRuntimeException(RuntimeException ex) {
        ResponseErrorDTO resposta = ResponseErrorDTO.builder()
        	.mensagem(ex.getMessage())
        	.traceId(UUID.randomUUID())
        	.timestamp(LocalDateTime.now())
        	.build();
		
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }
	
}
