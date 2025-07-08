package br.tec.db.desafio_votacao.domain.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Getter
public class VotoId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "ID_SESSAO_VOTACAO", updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private SessaoVotacao sessao;
	
	@JoinColumn(name = "ID_ASSOCIADO", updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Associado associado;
	
}
