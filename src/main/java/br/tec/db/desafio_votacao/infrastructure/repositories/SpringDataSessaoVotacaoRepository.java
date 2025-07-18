package br.tec.db.desafio_votacao.infrastructure.repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;

public interface SpringDataSessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long>, JpaSpecificationExecutor<SessaoVotacao>{

	boolean existsByDataHoraEncerramentoGreaterThanAndDataHoraEncerramentoLessThanEqualAndPautaId(
		LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Long idPauta
	);
	
	@EntityGraph(attributePaths = {"pauta"})
    @Override
    Page<SessaoVotacao> findAll(Specification<SessaoVotacao> spec, Pageable pageable);

	@EntityGraph(attributePaths = {"pauta"})
    @Override
	Optional<SessaoVotacao> findById(Long id);
	
}
