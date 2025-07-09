package br.tec.db.desafio_votacao.application.usecases.voto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.tec.db.desafio_votacao.application.dto.voto.request.VotoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.voto.response.VotoResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.VotoMapper;
import br.tec.db.desafio_votacao.domain.entities.Associado;
import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;
import br.tec.db.desafio_votacao.domain.entities.Voto;
import br.tec.db.desafio_votacao.domain.entities.VotoId;
import br.tec.db.desafio_votacao.domain.repositories.AssociadoRepository;
import br.tec.db.desafio_votacao.domain.repositories.SessaoVotacaoRepository;
import br.tec.db.desafio_votacao.domain.repositories.VotoRepository;
import br.tec.db.desafio_votacao.shared.exceptions.BusinessException;
import br.tec.db.desafio_votacao.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CadastrarVotoUseCase {

	private final AssociadoRepository associadoRepository;
	private final SessaoVotacaoRepository sessaoVotacaoRepository;
	private final VotoRepository votoRepository;
	private final VotoMapper votoMapper;
	
	@Transactional(timeout = 1)
	public VotoResponseDTO executar(final VotoRequestDTO dto) {
		Voto voto = prepararParaInclusao(dto);
		
		validaSeVotoPodeSerComputado(voto);
		
		return VotoResponseDTO.criar(votoRepository.salvar(voto));
	}
	
	private void validaSeVotoPodeSerComputado(final Voto voto) {
		if(votoRepository.associadoJaVotouNessaSessaoVotacao(voto.getId())) {
			throw new BusinessException("O associado informado já votou nesta sessão de votação.");
		}

		if(!voto.getId().getSessao().isEmAberto()){
			throw new BusinessException("Esta sessão de votação já foi encerrada e não pode mais receber votos.");
		}
	}
	
	private Voto prepararParaInclusao(final VotoRequestDTO dto) {
		final Voto voto = votoMapper.votoRequestDTOParaVoto(dto);
		
		voto.setId(obterIdentificadorVoto(dto));
		voto.setDataHoraInclusao(LocalDateTime.now());
		
		return voto;
	}
	
	private VotoId obterIdentificadorVoto(final VotoRequestDTO dto) {
		final Long idAssociado = Long.valueOf(dto.getIdAssociado());
		final Long idSessaoVotacao = Long.valueOf(dto.getIdSessaoVotacao());
		final Associado associado = obterAssociado(idAssociado);
		final SessaoVotacao sessaoVotacao = obterSessaoVotacao(idSessaoVotacao);
		
		return VotoId
			.builder()
			.associado(associado)
			.sessao(sessaoVotacao)
			.build()
		;
	}
	
	private Associado obterAssociado(final Long idAssociado) {
		return associadoRepository.obterPorId(idAssociado).orElseThrow(() -> new NotFoundException("Associado não encontrado"));
	}
	
	private SessaoVotacao obterSessaoVotacao(final Long idSessaoVotacao) {
		return sessaoVotacaoRepository.obterPorId(idSessaoVotacao).orElseThrow(() -> new NotFoundException("Sessão Votação não encontrada"));
	}
	
}
