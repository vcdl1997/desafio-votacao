package br.tec.db.desafio_votacao.infrastructure.repositories.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Repository;

import br.tec.db.desafio_votacao.domain.entities.Voto;
import br.tec.db.desafio_votacao.domain.entities.VotoId;
import br.tec.db.desafio_votacao.domain.repositories.VotoRepository;
import br.tec.db.desafio_votacao.domain.vo.FiltroVotoVO;
import br.tec.db.desafio_votacao.infrastructure.repositories.SpringDataVotoRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VotoRepositoryImpl implements VotoRepository {

	private final SpringDataVotoRepository jpaRepository;
	
	@Override
	public List<Voto> listarVotosPorSessaoVotacao(final FiltroVotoVO vo){
		if(Objects.nonNull(vo) && Objects.nonNull(vo.idSessaoVotacao())) {
			return jpaRepository.findByIdSessaoId(vo.idSessaoVotacao());
		}
		
		return Collections.emptyList();
	}
	
	@Override
	public Voto salvar(Voto voto) {
		return jpaRepository.save(voto);
	}

	@Override
	public boolean associadoJaVotouNessaSessaoVotacao(VotoId id) {
		return jpaRepository.existsById(id);
	}
	
}
