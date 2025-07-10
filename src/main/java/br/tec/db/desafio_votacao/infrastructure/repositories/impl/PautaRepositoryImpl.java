package br.tec.db.desafio_votacao.infrastructure.repositories.impl;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import br.tec.db.desafio_votacao.domain.entities.Pauta;
import br.tec.db.desafio_votacao.domain.repositories.PautaRepository;
import br.tec.db.desafio_votacao.domain.vo.FiltroPautaVO;
import br.tec.db.desafio_votacao.infrastructure.repositories.SpringDataPautaRepository;
import br.tec.db.desafio_votacao.infrastructure.repositories.specification.PautaSpecification;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PautaRepositoryImpl implements PautaRepository {

	private final SpringDataPautaRepository jpaRepository;
	
	@Override
	public Page<Pauta> listar(final FiltroPautaVO filtro, final Pageable pageable){
		return jpaRepository.findAll(obterSpecs(filtro), pageable);
	}
	
	private Specification<Pauta> obterSpecs(final FiltroPautaVO filtro) {
		final Long id = Objects.nonNull(filtro) ? filtro.id() : null;
		final String assunto = Objects.nonNull(filtro) ? filtro.assunto() : null;
		Specification<Pauta> specs = (root, query, builder) -> null;
		
		if(Objects.nonNull(id)){
			specs = specs.and(PautaSpecification.filtrarPorId(id));
		}
		
		if(StringUtils.isNotEmpty(assunto)) {
			specs = specs.and(PautaSpecification.filtrarPorAssunto(assunto));
		}
		
		return specs;
	}
	
	@Override
	public Optional<Pauta> obterPorId(Long id) {
		Objects.requireNonNull(id, "ID da Pauta não pode ser nulo");

		return jpaRepository.findById(id);
	}
	
	@Override
	public Pauta salvar(Pauta pauta) {
		return jpaRepository.save(pauta);
	}
	
}
