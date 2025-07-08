package br.tec.db.desafio_votacao.domain.repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;
import br.tec.db.desafio_votacao.domain.vo.FiltroSessaoVotacaoVO;

public interface SessaoVotacaoRepository {
	
	Page<SessaoVotacao> listar(FiltroSessaoVotacaoVO filtro, Pageable pageable);
	
	Optional<SessaoVotacao> obterPorId(Long id);
	
	SessaoVotacao salvar(SessaoVotacao sessaoVotacao);
	
	boolean existemSessoesAbertasParaEssaPauta(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Long idPauta);
	
}
