package br.tec.db.desafio_votacao.application.usecases.sessaovotacao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.tec.db.desafio_votacao.application.dto.sessaovotacao.request.FiltroSessaoVotacaoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.sessaovotacao.response.SessaoVotacaoResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.SessaoVotacaoMapper;
import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;
import br.tec.db.desafio_votacao.domain.repositories.SessaoVotacaoRepository;
import br.tec.db.desafio_votacao.domain.vo.FiltroSessaoVotacaoVO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ListarSessaoVotacaoPorIdPautaUseCase {

	private final SessaoVotacaoRepository repository;
	private final SessaoVotacaoMapper mapper;
	
	public Page<SessaoVotacaoResponseDTO> listar(final FiltroSessaoVotacaoRequestDTO dto, final Pageable pageable){
		FiltroSessaoVotacaoVO vo = mapper.filtroSessaoVotacaoRequestDTOParaFiltroSessaoVotacaoVO(dto);
		Page<SessaoVotacao> resposta = repository.listar(vo, pageable);
		return formatarResposta(resposta);
	}
	
	private Page<SessaoVotacaoResponseDTO> formatarResposta(final Page<SessaoVotacao> resposta){
		List<SessaoVotacaoResponseDTO> listaFormatada = converterParaDTO(resposta);
		return new PageImpl<>(listaFormatada, resposta.getPageable(), resposta.getSize());
	}
	
	private List<SessaoVotacaoResponseDTO> converterParaDTO(final Page<SessaoVotacao> resposta){
		return resposta
			.stream()
			.map(SessaoVotacaoResponseDTO::criar)
			.collect(Collectors.toList())
		;
	}
	
}
