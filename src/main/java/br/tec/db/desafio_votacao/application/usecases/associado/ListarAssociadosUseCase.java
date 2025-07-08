package br.tec.db.desafio_votacao.application.usecases.associado;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.tec.db.desafio_votacao.application.dto.associado.request.FiltroAssociadoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.associado.response.AssociadoResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.AssociadoMapper;
import br.tec.db.desafio_votacao.domain.entities.Associado;
import br.tec.db.desafio_votacao.domain.repositories.AssociadoRepository;
import br.tec.db.desafio_votacao.domain.vo.FiltroAssociadoVO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ListarAssociadosUseCase {
	
	private final AssociadoRepository repository;
	private final AssociadoMapper mapper;
	
	public Page<AssociadoResponseDTO> executar(final FiltroAssociadoRequestDTO dto, final Pageable pageable) {
		final FiltroAssociadoVO vo = mapper.filtroAssociadoDTOParaFiltroAssociadoVO(dto);
		final Page<Associado> resposta = repository.listar(vo, pageable);
		return formatarResposta(resposta);
    }
	
	private Page<AssociadoResponseDTO> formatarResposta(final Page<Associado> resposta){
		List<AssociadoResponseDTO> listaFormatada = converterParaDto(resposta);
	    return new PageImpl<>(listaFormatada, resposta.getPageable(), resposta.getTotalElements());
	}
	
	private List<AssociadoResponseDTO> converterParaDto(final Page<Associado> resposta){
		return resposta
			.getContent()
			.stream()
			.map(associado -> mapper.associadoParaAssociadoResponseDTO(associado))
			.collect(Collectors.toList())
		;
	}

}
