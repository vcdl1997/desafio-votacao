package br.tec.db.desafio_votacao.application.usecases.pauta;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.tec.db.desafio_votacao.application.dto.pauta.request.FiltroPautaRequestDTO;
import br.tec.db.desafio_votacao.application.dto.pauta.response.PautaResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.PautaMapper;
import br.tec.db.desafio_votacao.domain.entities.Pauta;
import br.tec.db.desafio_votacao.domain.repositories.PautaRepository;
import br.tec.db.desafio_votacao.domain.vo.FiltroPautaVO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ListarPautasUseCase {

	private final PautaRepository repository;
	private final PautaMapper mapper;
	
	public Page<PautaResponseDTO> executar(final FiltroPautaRequestDTO dto, final Pageable pageable) {
		FiltroPautaVO vo = mapper.filtroPautaRequestDTOParaFiltroPautaVO(dto);
		Page<Pauta> resposta = repository.listar(vo, pageable);
		return formatarResposta(resposta);
	}
	
	private Page<PautaResponseDTO> formatarResposta(final Page<Pauta> resposta){
		List<PautaResponseDTO> listaFormatada = converterParaDto(resposta);
	    return new PageImpl<>(listaFormatada, resposta.getPageable(), resposta.getTotalElements());
	}
	
	private List<PautaResponseDTO> converterParaDto(final Page<Pauta> resposta){
		return resposta
			.getContent()
			.stream()
			.map(pauta -> mapper.pautaParaPautaResponseDTO(pauta))
			.collect(Collectors.toList())
		;
	}
	
}
