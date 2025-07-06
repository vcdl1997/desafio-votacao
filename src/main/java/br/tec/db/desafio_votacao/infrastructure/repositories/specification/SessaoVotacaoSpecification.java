package br.tec.db.desafio_votacao.infrastructure.repositories.specification;

import org.springframework.data.jpa.domain.Specification;

import br.tec.db.desafio_votacao.domain.entities.Pauta_;
import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;
import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao_;

public class SessaoVotacaoSpecification {
	
	public static Specification<SessaoVotacao> filtrarPorId(Long id) {
	    return (root, query, builder) -> {
	      return builder.equal(root.get(SessaoVotacao_.id), id);
	    };
	}
	
	public static Specification<SessaoVotacao> filtrarPorIdPauta(Long idPauta) {
	    return (root, query, builder) -> {
	    	return builder.equal(root.get(SessaoVotacao_.pauta).get(Pauta_.id), idPauta);
	    };
	}
	
}
