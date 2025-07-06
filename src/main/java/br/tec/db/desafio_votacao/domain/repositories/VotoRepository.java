package br.tec.db.desafio_votacao.domain.repositories;

import java.util.List;

import br.tec.db.desafio_votacao.domain.entities.Voto;

public interface VotoRepository {
	
	public List<Voto> listarPorIdSessaoVotacao(Long idSessaoVotacao);
	
	public Voto salvar(Voto voto);
	
}
