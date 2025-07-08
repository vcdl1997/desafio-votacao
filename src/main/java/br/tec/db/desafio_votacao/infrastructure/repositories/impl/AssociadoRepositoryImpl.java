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

	private final SpringDataAssociadoRepository jpaRepository;
	
	@Override
	public Page<Associado> listar(final FiltroAssociadoVO filtro, final Pageable pageable){
		return jpaRepository.findAll(obterSpecs(filtro), pageable);
	}
	
	private Specification<Associado> obterSpecs(final FiltroAssociadoVO filtro) {
		final Long id = Objects.nonNull(filtro) ? filtro.id() : null;
		final Long cpf = Objects.nonNull(filtro) ? filtro.cpf() : null;
		final String nome = Objects.nonNull(filtro) ? filtro.nome() : null;
		final String email = Objects.nonNull(filtro) ? filtro.email() : null;
		Specification<Associado> specs = (root, query, builder) -> null;
		
		if(Objects.nonNull(id)){
			specs = specs.and(AssociadoSpecification.filtrarPorId(id));
		}
		
		if(Objects.nonNull(cpf)){
			specs = specs.and(AssociadoSpecification.filtrarPorCpf(cpf));
		}
		
		if(StringUtils.isNotEmpty(nome)) {
			specs = specs.and(AssociadoSpecification.filtrarPorNome(nome));
		}
		
		if(StringUtils.isNotEmpty(email)) {
			specs = specs.and(AssociadoSpecification.filtrarPorEmail(email));
		}
		
		return specs;
	}
	
	@Override
	public Optional<Associado> obterPorId(Long id) {
		Objects.requireNonNull(id, "ID do Associado não foi informado");

		return jpaRepository.findById(id);
	}
	
	@Override
	public Associado salvar(Associado associado) {
		return jpaRepository.save(associado);
	}
	
}
