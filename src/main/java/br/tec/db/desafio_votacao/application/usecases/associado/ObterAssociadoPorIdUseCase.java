package br.tec.db.desafio_votacao.application.usecases.associado;

import org.springframework.stereotype.Component;

import br.tec.db.desafio_votacao.application.dto.associado.response.AssociadoResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.AssociadoMapper;
import br.tec.db.desafio_votacao.domain.entities.Associado;
import br.tec.db.desafio_votacao.domain.repositories.AssociadoRepository;
import br.tec.db.desafio_votacao.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ObterAssociadoPorIdUseCase {

	private final AssociadoRepository repository;
	private final AssociadoMapper mapper;
	
	public AssociadoResponseDTO executar(Long id) {
		Associado associado = repository.obterPorId(id).orElseThrow(() -> new NotFoundException("Associado não encontrado"));
		return mapper.associadoParaAssociadoResponseDTO(associado);
    }
	
}
