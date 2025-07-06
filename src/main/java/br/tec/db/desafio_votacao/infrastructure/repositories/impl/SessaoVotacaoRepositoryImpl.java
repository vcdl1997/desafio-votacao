package br.tec.db.desafio_votacao.infrastructure.repositories.impl;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;
import br.tec.db.desafio_votacao.domain.repositories.SessaoVotacaoRepository;
import br.tec.db.desafio_votacao.domain.vo.FiltroSessaoVotacaoVO;
import br.tec.db.desafio_votacao.infrastructure.repositories.SpringDataSessaoVotacaoRepository;
import br.tec.db.desafio_votacao.infrastructure.repositories.specification.SessaoVotacaoSpecification;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SessaoVotacaoRepositoryImpl implements SessaoVotacaoRepository {

	private SpringDataSessaoVotacaoRepository jpaRepository;
	
	@Override
	public Page<SessaoVotacao> listar(final FiltroSessaoVotacaoVO filtro, final Pageable pageable){
		return jpaRepository.findAll(obterSpecs(filtro), pageable);
	}
	
	private Specification<SessaoVotacao> obterSpecs(final FiltroSessaoVotacaoVO filtro) {
		Specification<SessaoVotacao> specs = (root, query, builder) -> null;
		
		if(Objects.nonNull(filtro.getIdSessao())){
			specs = specs.and(SessaoVotacaoSpecification.filtrarPorId(filtro.getIdSessao()));
		}
		
		if(Objects.nonNull(filtro.getIdPauta())) {
			specs = specs.and(SessaoVotacaoSpecification.filtrarPorIdPauta(filtro.getIdPauta()));
		}
		
		return specs;
	}
	
	@Override
	public SessaoVotacao salvar(SessaoVotacao sessaoVotacao) {
		return jpaRepository.save(sessaoVotacao);
	}
	
}
