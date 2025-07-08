package br.tec.db.desafio_votacao.infrastructure.repositories;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;

public interface SpringDataSessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long>, JpaSpecificationExecutor<SessaoVotacao>{

	boolean existsByDataHoraEncerramentoGreaterThanAndDataHoraEncerramentoLessThanEqualAndPautaId(
		LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Long idPauta
	);
	
}
