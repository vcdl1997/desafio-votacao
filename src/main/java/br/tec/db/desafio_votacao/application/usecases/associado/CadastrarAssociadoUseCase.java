package br.tec.db.desafio_votacao.application.usecases.associado;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.tec.db.desafio_votacao.application.dto.associado.request.CadastroAssociadoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.associado.response.AssociadoResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.AssociadoMapper;
import br.tec.db.desafio_votacao.domain.entities.Associado;
import br.tec.db.desafio_votacao.domain.repositories.AssociadoRepository;
import br.tec.db.desafio_votacao.shared.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CadastrarAssociadoUseCase {
	
	private final AssociadoRepository repository;
	private final AssociadoMapper mapper;

	@Transactional(timeout = 1)
	public AssociadoResponseDTO executar(final CadastroAssociadoRequestDTO dto) {
		Associado associado = prepararAssociadoParaInclusao(dto);

		validaSeExistemAssociadosUsandoEsteCpf(associado.getCpf());
		
		return mapper.associadoParaAssociadoResponseDTO(repository.salvar(associado));
    }
	
	private Associado prepararAssociadoParaInclusao(final CadastroAssociadoRequestDTO dto) {
		Associado associado = mapper.cadastroAssociadoRequestDTOParaAssociado(dto);
		associado.setDataHoraInclusao(LocalDateTime.now());
		return associado;
	}

	private void validaSeExistemAssociadosUsandoEsteCpf(final Long cpf) {
		if(repository.existemUsuariosComEsteCpf(cpf)){
			throw new BusinessException("Este CPF já está em uso por outro associado.");
		}
	}
	
}
