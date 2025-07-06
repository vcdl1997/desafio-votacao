package br.tec.db.desafio_votacao.infrastructure.repositories.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.tec.db.desafio_votacao.domain.entities.Voto;
import br.tec.db.desafio_votacao.domain.repositories.VotoRepository;
import br.tec.db.desafio_votacao.infrastructure.repositories.SpringDataVotoRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VotoRepositoryImpl implements VotoRepository {

	private SpringDataVotoRepository jpaRepository;
	
	@Override
	public List<Voto> listarPorIdSessaoVotacao(final Long idSessaoVotacao){
		return jpaRepository.findById_SessaoId(idSessaoVotacao);
	}
	
	@Override
	public Voto salvar(Voto voto) {
		return jpaRepository.save(voto);
	}
	
}
