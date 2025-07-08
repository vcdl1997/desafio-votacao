package br.tec.db.desafio_votacao.application.dto.sessaovotacao.response;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;
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
public class SessaoVotacaoResponseDTO {

	private Long id;
	
	private String assunto;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="GMT-3")
	private LocalDateTime dataHoraEncerramento;
	
	private boolean emAberto;
	
	public static SessaoVotacaoResponseDTO criar(SessaoVotacao sessaoVotacao) {
		Objects.requireNonNull(sessaoVotacao, "SessaoVotacao não pode ser nulo");
		
		String assunto = Objects.nonNull(sessaoVotacao.getPauta()) ? sessaoVotacao.getPauta().getAssunto() : null;
		
		return SessaoVotacaoResponseDTO
			.builder()
			.id(sessaoVotacao.getId())
			.assunto(assunto)
			.dataHoraEncerramento(sessaoVotacao.getDataHoraEncerramento())
			.emAberto(sessaoVotacao.isEmAberto())
			.build()
		;
	}
	
}
