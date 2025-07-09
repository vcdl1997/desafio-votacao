package br.tec.db.desafio_votacao.domain.entities;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import br.tec.db.desafio_votacao.shared.exceptions.BusinessException;
import br.tec.db.desafio_votacao.shared.utils.ObjetoUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(schema = "GOVERNANCA", name = "SESSAO_VOTACAO")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SessaoVotacao extends AbstractEntity<Long> {

	@JoinColumn(name = "ID_PAUTA")
	@ManyToOne(fetch = FetchType.LAZY)
	private Pauta pauta;
	
	@Column(name = "DT_HR_ENCERRAMENTO", updatable = false)
	private LocalDateTime dataHoraEncerramento;
	
	public boolean isEmAberto() {
		LocalDateTime dataHoraAtual = LocalDateTime.now();
		LocalDateTime dataHoraAtualSemSegundos = dataHoraAtual.truncatedTo(ChronoUnit.MINUTES);
		return Objects.isNull(dataHoraEncerramento) ? false : dataHoraAtualSemSegundos.isBefore(dataHoraEncerramento);
	}
	
	public void validarRegrasDeNegocioParaInclusao() {
		ObjetoUtils.requireNonNull("Valor não informado", dataHoraEncerramento);
		
		if (Objects.isNull(dataHoraEncerramento)) {
	        throw new BusinessException("A dataHoraEncerramento não pode ser nula");
	    }

	    if (dataHoraEncerramento.isBefore(LocalDateTime.now())) {
	        throw new BusinessException("A propriedade dataHoraEncerramento deve ser maior que a data hora atual");
	    }
		
	}
	
}
