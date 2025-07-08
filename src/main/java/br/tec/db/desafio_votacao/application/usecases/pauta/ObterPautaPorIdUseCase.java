package br.tec.db.desafio_votacao.application.usecases.pauta;

import org.springframework.stereotype.Component;

import br.tec.db.desafio_votacao.application.dto.pauta.response.PautaResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.PautaMapper;
import br.tec.db.desafio_votacao.domain.entities.Pauta;
import br.tec.db.desafio_votacao.domain.repositories.PautaRepository;
import br.tec.db.desafio_votacao.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ObterPautaPorIdUseCase {
	
	private final PautaRepository repository;
	private final PautaMapper mapper;
	
	public PautaResponseDTO executar(final Long id) {
		Pauta pauta = repository.obterPorId(id).orElseThrow(() -> new NotFoundException("Pauta não encontrada"));
		return mapper.pautaParaPautaResponseDTO(pauta);
	}

}
