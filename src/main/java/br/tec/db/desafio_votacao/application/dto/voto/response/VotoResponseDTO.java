package br.tec.db.desafio_votacao.application.dto.voto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.tec.db.desafio_votacao.domain.entities.Voto;
import br.tec.db.desafio_votacao.domain.enums.RespostaVotoEnum;
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
public class VotoResponseDTO {
	
	private static final String MENSAGEM_DEFAULT = "Voto computado com sucesso.";
	
	private String mensagem;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="GMT-3")
	private LocalDateTime dataHoraRegistro;
	
	private String resposta;
	
	private Long quantidade;
	
	private String pauta;
	
	private List<VotoResponseDTO> resultado;
	
	public static VotoResponseDTO criar(Voto voto) {
		return VotoResponseDTO
			.builder()
			.mensagem(MENSAGEM_DEFAULT)
			.dataHoraRegistro(voto.getDataHoraInclusao())
			.build()
		;
	}
	
	public static VotoResponseDTO criar(RespostaVotoEnum resposta, Long quantidade) {
		return VotoResponseDTO
			.builder()
			.resposta(resposta.obterDescricao())
			.quantidade(quantidade)
			.build()
		;
	}
	
	public static VotoResponseDTO criar(String pauta, List<VotoResponseDTO> resultadoVotacao) {
		return VotoResponseDTO
			.builder()
			.pauta(pauta)
			.resultado(resultadoVotacao)
			.build()
		;
	}
	
}
