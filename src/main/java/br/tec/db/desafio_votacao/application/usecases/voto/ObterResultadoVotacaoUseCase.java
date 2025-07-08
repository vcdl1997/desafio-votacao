package br.tec.db.desafio_votacao.application.usecases.voto;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import br.tec.db.desafio_votacao.application.dto.voto.request.FiltroVotoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.voto.response.VotoResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.VotoMapper;
import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;
import br.tec.db.desafio_votacao.domain.entities.Voto;
import br.tec.db.desafio_votacao.domain.enums.RespostaVotoEnum;
import br.tec.db.desafio_votacao.domain.repositories.SessaoVotacaoRepository;
import br.tec.db.desafio_votacao.domain.repositories.VotoRepository;
import br.tec.db.desafio_votacao.domain.vo.FiltroVotoVO;
import br.tec.db.desafio_votacao.shared.exceptions.BusinessException;
import br.tec.db.desafio_votacao.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ObterResultadoVotacaoUseCase {
	
	private final SessaoVotacaoRepository sessaoVotacaoRepository;
	private final VotoRepository votoRepository;
	private final VotoMapper mapper;
	
	public VotoResponseDTO executar(final FiltroVotoRequestDTO dto) {
		FiltroVotoVO vo = mapper.filtroVotoRequestDTOParaFiltroVotoVO(dto);
		
		SessaoVotacao sessaoVotacao = obterSessaoVotacao(vo);
		
		verificaSeSessaoVotacaoFoiEncerrada(sessaoVotacao);
		
		List<Voto> votos = votoRepository.listarVotosPorSessaoVotacao(vo);
		
		return formatarResposta(votos, sessaoVotacao);
	}
	
	private SessaoVotacao obterSessaoVotacao(final FiltroVotoVO vo) {
		return sessaoVotacaoRepository
			.obterPorId(vo.idSessaoVotacao())
			.orElseThrow(() -> new NotFoundException("Sessão Votação não encontrada"))
		;
	}

	private VotoResponseDTO formatarResposta(final List<Voto> votos, final SessaoVotacao sessaoVotacao){
		String pauta = sessaoVotacao.getPauta().getAssunto();
		List<VotoResponseDTO> resultado = obterResultadoVotacao(votos);
	    return VotoResponseDTO.criar(pauta, resultado);
	}
	
	private void verificaSeSessaoVotacaoFoiEncerrada(final SessaoVotacao sessaoVotacao){
		if(sessaoVotacao.isEmAberto()) {
			throw new BusinessException(
				"Não é possível obter o resultado da votação, pois a sessão ainda se encontra em andamento"
			);
		}
	}
	
	private List<VotoResponseDTO> obterResultadoVotacao(final List<Voto> votos){
		long totalSim = obterTotalResposta(votos, RespostaVotoEnum.SIM);
		long totalNao = obterTotalResposta(votos, RespostaVotoEnum.NAO);
		
	    return List.of(
	    	VotoResponseDTO.criar(RespostaVotoEnum.SIM, totalSim),
	    	VotoResponseDTO.criar(RespostaVotoEnum.NAO, totalNao)
	    );
	}
	
	private long obterTotalResposta(final List<Voto> votos, final RespostaVotoEnum resposta){
		return votos
			.parallelStream()
			.filter(voto -> Objects.equals(voto.getResposta(), resposta))
			.count();
	}
}
