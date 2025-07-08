package br.tec.db.desafio_votacao.application.usecases.associado;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.tec.db.desafio_votacao.application.dto.associado.request.CadastroAssociadoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.associado.response.AssociadoResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.AssociadoMapper;
import br.tec.db.desafio_votacao.domain.entities.Associado;
import br.tec.db.desafio_votacao.domain.repositories.AssociadoRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CadastrarAssociadoUseCase {
	
	private final AssociadoRepository repository;
	private final AssociadoMapper mapper;

	@Transactional(timeout = 1)
	public AssociadoResponseDTO executar(final CadastroAssociadoRequestDTO dto) {
		Associado associado = repository.salvar(prepararAssociadoParaInclusao(dto));
		return mapper.associadoParaAssociadoResponseDTO(associado);
    }
	
	private Associado prepararAssociadoParaInclusao(final CadastroAssociadoRequestDTO dto) {
		Associado associado = mapper.cadastroAssociadoRequestDTOParaAssociado(dto);
		associado.setDataHoraInclusao(LocalDateTime.now());
		return associado;
	}
	
}
