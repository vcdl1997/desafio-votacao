package br.tec.db.desafio_votacao.infrastructure.repositories.impl;

import java.util.Objects;
import java.util.Optional;

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

	private final SpringDataSessaoVotacaoRepository jpaRepository;
	
	@Override
	public Page<SessaoVotacao> listar(final FiltroSessaoVotacaoVO filtro, final Pageable pageable){
		return jpaRepository.findAll(obterSpecs(filtro), pageable);
	}
	
	private Specification<SessaoVotacao> obterSpecs(final FiltroSessaoVotacaoVO filtro) {
		final Long idSessaoVotacao = Objects.nonNull(filtro) ? filtro.idSessaoVotacao() : null;
		final Long idPauta = Objects.nonNull(filtro) ? filtro.idPauta() : null;
		Specification<SessaoVotacao> specs = (root, query, builder) -> null;
		
		if(Objects.nonNull(idSessaoVotacao)){
			specs = specs.and(SessaoVotacaoSpecification.filtrarPorId(idSessaoVotacao));
		}
		
		if(Objects.nonNull(idPauta)) {
			specs = specs.and(SessaoVotacaoSpecification.filtrarPorIdPauta(idPauta));
		}
		
		return specs;
	}
	
	@Override
	public Optional<SessaoVotacao> obterPorId(Long id) {
		return jpaRepository.findById(id);
	}
	
	@Override
	public SessaoVotacao salvar(SessaoVotacao sessaoVotacao) {
		return jpaRepository.save(sessaoVotacao);
	}
	
}
