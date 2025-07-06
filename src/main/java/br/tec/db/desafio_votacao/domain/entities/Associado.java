package br.tec.db.desafio_votacao.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(schema = "GOVERNANCA", name = "ASSOCIADO")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Associado extends AbstractEntity<Long>{
	
	@Column(name = "CPF", unique = true, updatable = false)
	private Long cpf;

	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "VERSAO")
	@Version
	private Short versao;
	
}
