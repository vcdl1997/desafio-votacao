package br.tec.db.desafio_votacao.domain.entities;

import java.time.LocalDateTime;

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
@Table(schema = "GOVERNANCA", name = "SESSAO")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SessaoVotacao extends AbstractEntity<Long> {

	@JoinColumn(name = "ID_PAUTA")
	@ManyToOne(fetch = FetchType.LAZY)
	private Pauta pauta;
	
	@Column(name = "DT_HR_INICIO", updatable = false)
	private LocalDateTime dataHoraInicio;
	
	@Column(name = "DT_HR_ENCERRAMENTO", updatable = false)
	private LocalDateTime dataHoraEncerramento;
	
}
