package br.tec.db.desafio_votacao.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(schema = "GOVERNANCA", name = "PAUTA")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pauta extends AbstractEntity<Long> {
	
	@Column(name = "ASSUNTO")
	private String assunto;
	
}
