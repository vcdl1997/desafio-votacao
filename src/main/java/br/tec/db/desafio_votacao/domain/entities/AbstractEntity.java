package br.tec.db.desafio_votacao.domain.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AbstractEntity<T>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private T id;
	
	@Column(name = "DT_HR_INCLUSAO")
	private LocalDateTime dataHoraInclusao;
	
	protected AbstractEntity<T> atualizar() {
		return null;
	}
	
	protected Class<? extends AbstractEntity<T>> atualizar(Class<? extends AbstractEntity<T>> registro) {
		 return null;
	}
	
}
