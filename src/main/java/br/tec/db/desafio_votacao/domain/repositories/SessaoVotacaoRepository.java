package br.tec.db.desafio_votacao.domain.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;
import br.tec.db.desafio_votacao.domain.vo.FiltroSessaoVotacaoVO;

public interface SessaoVotacaoRepository {
	
	public Page<SessaoVotacao> listar(FiltroSessaoVotacaoVO filtro, Pageable pageable);
	
	public Optional<SessaoVotacao> obterPorId(Long id);
	
	public SessaoVotacao salvar(SessaoVotacao sessaoVotacao);
	
}
