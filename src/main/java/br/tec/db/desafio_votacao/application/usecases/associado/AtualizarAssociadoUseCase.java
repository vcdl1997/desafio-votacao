package br.tec.db.desafio_votacao.application.usecases.associado;

import java.util.Objects;

import org.springframework.stereotype.Component;

import br.tec.db.desafio_votacao.application.dto.associado.request.AtualizacaoAssociadoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.associado.response.AssociadoResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.AssociadoMapper;
import br.tec.db.desafio_votacao.domain.entities.Associado;
import br.tec.db.desafio_votacao.domain.repositories.AssociadoRepository;
import br.tec.db.desafio_votacao.shared.exceptions.NotFoundException;
import br.tec.db.desafio_votacao.shared.utils.ObjetoUtils;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AtualizarAssociadoUseCase {

	private final AssociadoRepository repository;
	private final AssociadoMapper mapper;

	public AssociadoResponseDTO executar(Long id, AtualizacaoAssociadoRequestDTO dto) {
		Associado associado = prepararAssociadoParaAtualizacao(obterUsuarioPorId(id), dto);
		return mapper.associadoParaAssociadoResponseDTO(associado);
    }
	
	private Associado obterUsuarioPorId(Long id) {
		Objects.requireNonNull(id, "ID do Associado não foi informado!");
		
		return repository.obterPorId(id).orElseThrow(() -> new NotFoundException("Associado não encontrado"));
	}
	
	private Associado prepararAssociadoParaAtualizacao(final Associado associado, final AtualizacaoAssociadoRequestDTO dto) {
		ObjetoUtils.requireNonNull("Valor não informado", dto.getNome(), dto.getEmail());
		
		associado.setNome(dto.getNome());
		associado.setEmail(dto.getEmail());
		
		return associado;
	}
	
}
