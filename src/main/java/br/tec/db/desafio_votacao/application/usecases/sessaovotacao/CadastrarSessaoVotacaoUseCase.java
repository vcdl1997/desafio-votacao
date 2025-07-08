package br.tec.db.desafio_votacao.application.usecases.sessaovotacao;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.stereotype.Component;

import br.tec.db.desafio_votacao.application.dto.sessaovotacao.request.CadastroSessaoVotacaoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.sessaovotacao.response.SessaoVotacaoResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.SessaoVotacaoMapper;
import br.tec.db.desafio_votacao.domain.entities.Pauta;
import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;
import br.tec.db.desafio_votacao.domain.repositories.PautaRepository;
import br.tec.db.desafio_votacao.domain.repositories.SessaoVotacaoRepository;
import br.tec.db.desafio_votacao.shared.exceptions.BusinessException;
import br.tec.db.desafio_votacao.shared.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CadastrarSessaoVotacaoUseCase {

	private final PautaRepository pautaRepository;
	private final SessaoVotacaoRepository sessaoVotacaoRepository;
	private final SessaoVotacaoMapper sessaoVotacaoMapper;
	
	@Transactional
	public SessaoVotacaoResponseDTO cadastrar(final CadastroSessaoVotacaoRequestDTO dto) {
		SessaoVotacao sessaoVotacao = prepararSessaoVotacaoParaInclusao(dto);
		
		sessaoVotacao.validarRegrasDeNegocioParaInclusao();
		
		validaSeExistemSessoesAbertas(sessaoVotacao);
		
		return SessaoVotacaoResponseDTO.criar(sessaoVotacaoRepository.salvar(sessaoVotacao));
	}
	
	private void validaSeExistemSessoesAbertas(final SessaoVotacao sessaoVotacao) {
		final LocalDateTime dataHoraInicio = LocalDateTime.now();
		final LocalDateTime dataHoraFim = sessaoVotacao.getDataHoraEncerramento();
		final Long idPauta = sessaoVotacao.getPauta().getId();
		
		if(sessaoVotacaoRepository.existemSessoesAbertasParaEssaPauta(dataHoraInicio, dataHoraFim, idPauta)) {
			throw new BusinessException("A pauta já possui uma sessão de votação em andamento.");
		}
	}
	
	private SessaoVotacao prepararSessaoVotacaoParaInclusao(final CadastroSessaoVotacaoRequestDTO dto) {
		Pauta pauta = obterPauta(dto);
		
		SessaoVotacao sessaoVotacao = sessaoVotacaoMapper.cadastroSessaoVotacaoRequestDTOParaSessaoVotacao(dto);
		sessaoVotacao.setPauta(pauta);
		sessaoVotacao.setDataHoraInclusao(LocalDateTime.now());
		
		if(Objects.isNull(sessaoVotacao.getDataHoraEncerramento())) {
			sessaoVotacao.setDataHoraEncerramento(LocalDateTime.now().plusMinutes(1));
		}
		
		return sessaoVotacao;
	}
	
	private Pauta obterPauta(final CadastroSessaoVotacaoRequestDTO dto) {
		Long idPauta = Long.valueOf(dto.getIdPauta());
		return pautaRepository.obterPorId(idPauta).orElseThrow(() -> new NotFoundException("Pauta não encontrada."));
	}
	
}
