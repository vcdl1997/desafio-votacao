package br.tec.db.desafio_votacao.domain.entities;

import br.tec.db.desafio_votacao.domain.enums.RespostaVotoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "GOVERNANCA", name = "VOTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Voto {

	@EmbeddedId
	private VotoId id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "RESPOSTA", updatable = false)
	private RespostaVotoEnum resposta;
	
}
