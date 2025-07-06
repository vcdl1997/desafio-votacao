package br.tec.db.desafio_votacao.infrastructure.repositories.specification;

import org.springframework.data.jpa.domain.Specification;

import br.tec.db.desafio_votacao.domain.entities.Pauta;
import br.tec.db.desafio_votacao.domain.entities.Pauta_;

public class PautaSpecification {
	
	public static Specification<Pauta> filtrarPorId(Long id) {
	    return (root, query, builder) -> {
	      return builder.equal(root.get(Pauta_.id), id);
	    };
	}
	
	public static Specification<Pauta> filtrarPorAssunto(String assunto) {
	    return (root, query, builder) -> {
	      return builder.like(root.get(Pauta_.assunto), "%" +  assunto + "%");
	    };
	}
	
}
