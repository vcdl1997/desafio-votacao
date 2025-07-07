package br.tec.db.desafio_votacao.application.usecases.sessaovotacao;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.tec.db.desafio_votacao.application.dto.sessaovotacao.request.CadastroSessaoVotacaoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.sessaovotacao.response.SessaoVotacaoResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.SessaoVotacaoMapper;
import br.tec.db.desafio_votacao.domain.entities.Pauta;
import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;
import br.tec.db.desafio_votacao.domain.repositories.PautaRepository;
import br.tec.db.desafio_votacao.domain.repositories.SessaoVotacaoRepository;
import br.tec.db.desafio_votacao.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CadastrarSessaoVotacaoUseCase {

	private final PautaRepository pautaRepository;
	private final SessaoVotacaoRepository sessaoVotacaoRepository;
	private final SessaoVotacaoMapper sessaoVotacaoMapper;
	
	public SessaoVotacaoResponseDTO cadastrar(CadastroSessaoVotacaoRequestDTO dto) {
		SessaoVotacao sessaoVotacao = sessaoVotacaoRepository.salvar(prepararSessaoVotacaoParaInclusao(dto));
		return SessaoVotacaoResponseDTO.criar(sessaoVotacao);
	}
	
	private SessaoVotacao prepararSessaoVotacaoParaInclusao(CadastroSessaoVotacaoRequestDTO dto) {
		Pauta pauta = obterPauta(dto);
		
		SessaoVotacao sessaoVotacao = sessaoVotacaoMapper.cadastroSessaoVotacaoRequestDTOParaSessaoVotacao(dto);
		sessaoVotacao.setPauta(pauta);
		sessaoVotacao.setDataHoraInclusao(LocalDateTime.now());
		return sessaoVotacao;
	}
	
	private Pauta obterPauta(CadastroSessaoVotacaoRequestDTO dto) {
		Long idPauta = Long.valueOf(dto.getIdPauta());
		return pautaRepository.obterPorId(idPauta).orElseThrow(() -> new NotFoundException("Pauta não encontrada."));
	}
	
}
