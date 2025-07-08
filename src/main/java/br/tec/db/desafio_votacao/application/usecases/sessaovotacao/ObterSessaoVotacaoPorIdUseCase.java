package br.tec.db.desafio_votacao.application.usecases.sessaovotacao;

import org.springframework.stereotype.Component;

import br.tec.db.desafio_votacao.application.dto.sessaovotacao.response.SessaoVotacaoResponseDTO;
import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;
import br.tec.db.desafio_votacao.domain.repositories.SessaoVotacaoRepository;
import br.tec.db.desafio_votacao.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ObterSessaoVotacaoPorIdUseCase {
	
	private final SessaoVotacaoRepository repository;
	
	public SessaoVotacaoResponseDTO executar(final Long id) {
		SessaoVotacao sessaoVotacao = repository.obterPorId(id).orElseThrow(() -> new NotFoundException("Sessão Votação não encontrada"));
		return SessaoVotacaoResponseDTO.criar(sessaoVotacao);
	}

}
