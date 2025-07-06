package br.tec.db.desafio_votacao.infrastructure.repositories.specification;

import org.springframework.data.jpa.domain.Specification;

import br.tec.db.desafio_votacao.domain.entities.Associado;
import br.tec.db.desafio_votacao.domain.entities.Associado_;

public class AssociadoSpecification {
	
	public static Specification<Associado> filtrarPorId(Long id) {
	    return (root, query, builder) -> {
	      return builder.equal(root.get(Associado_.id), id);
	    };
	}
	
	public static Specification<Associado> filtrarPorCpf(Long cpf) {
	    return (root, query, builder) -> {
	      return builder.equal(root.get(Associado_.cpf), cpf);
	    };
	}

	public static Specification<Associado> filtrarPorNome(String nome) {
	    return (root, query, builder) -> {
	      return builder.like(root.get(Associado_.nome), "%" + nome + "%");
	    };
	}
	
	public static Specification<Associado> filtrarPorEmail(String email) {
	    return (root, query, builder) -> {
	      return builder.like(root.get(Associado_.email), "%" + email + "%");
	    };
	}
	
}
