package br.tec.db.desafio_votacao.infrastructure.repositories.impl;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import br.tec.db.desafio_votacao.domain.entities.Associado;
import br.tec.db.desafio_votacao.domain.repositories.AssociadoRepository;
import br.tec.db.desafio_votacao.domain.vo.FiltroAssociadoVO;
import br.tec.db.desafio_votacao.infrastructure.repositories.SpringDataAssociadoRepository;
import br.tec.db.desafio_votacao.infrastructure.repositories.specification.AssociadoSpecification;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AssociadoRepositoryImpl implements AssociadoRepository {

	private SpringDataAssociadoRepository jpaRepository;
	
	@Override
	public Page<Associado> listar(final FiltroAssociadoVO filtro, final Pageable pageable){
		return jpaRepository.findAll(obterSpecs(filtro), pageable);
	}
	
	private Specification<Associado> obterSpecs(final FiltroAssociadoVO filtro) {
		Specification<Associado> specs = (root, query, builder) -> null;
		
		if(Objects.nonNull(filtro.getId())){
			specs = specs.and(AssociadoSpecification.filtrarPorCpf(filtro.getId()));
		}
		
		if(Objects.nonNull(filtro.getCpf())){
			specs = specs.and(AssociadoSpecification.filtrarPorCpf(filtro.getCpf()));
		}
		
		if(StringUtils.isNotEmpty(filtro.getNome())) {
			specs = specs.and(AssociadoSpecification.filtrarPorNome(filtro.getNome()));
		}
		
		if(StringUtils.isNotEmpty(filtro.getEmail())) {
			specs = specs.and(AssociadoSpecification.filtrarPorEmail(filtro.getEmail()));
		}
		
		return specs;
	}
	
	@Override
	public Optional<Associado> obterPorId(Long id) {
		return jpaRepository.findById(id);
	}
	
	@Override
	public Associado salvar(Associado associado) {
		return jpaRepository.save(associado);
	}
	
	@Override
	public void deletar(Associado associado) {
		jpaRepository.delete(associado);
	}
	
}
