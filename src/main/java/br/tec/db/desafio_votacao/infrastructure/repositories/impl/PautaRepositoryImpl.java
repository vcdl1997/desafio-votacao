package br.tec.db.desafio_votacao.infrastructure.repositories.impl;

import java.util.Objects;

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

	private SpringDataPautaRepository jpaRepository;
	
	@Override
	public Page<Pauta> listar(final FiltroPautaVO filtro, final Pageable pageable){
		return jpaRepository.findAll(obterSpecs(filtro), pageable);
	}
	
	private Specification<Pauta> obterSpecs(final FiltroPautaVO filtro) {
		Specification<Pauta> specs = (root, query, builder) -> null;
		
		if(Objects.nonNull(filtro.getId())){
			specs = specs.and(PautaSpecification.filtrarPorId(filtro.getId()));
		}
		
		if(StringUtils.isNotEmpty(filtro.getAssunto())) {
			specs = specs.and(PautaSpecification.filtrarPorAssunto(filtro.getAssunto()));
		}
		
		return specs;
	}
	
	@Override
	public Pauta salvar(Pauta pauta) {
		return jpaRepository.save(pauta);
	}
	
}
