package br.tec.db.desafio_votacao.application.usecases.pauta;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.tec.db.desafio_votacao.application.dto.pauta.request.CadastroPautaRequestDTO;
import br.tec.db.desafio_votacao.application.dto.pauta.response.PautaResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.PautaMapper;
import br.tec.db.desafio_votacao.domain.entities.Pauta;
import br.tec.db.desafio_votacao.domain.repositories.PautaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CadastrarPautaUseCase {
	
	private final PautaRepository repository;
	private final PautaMapper mapper;
	
	@Transactional
	public PautaResponseDTO executar(final CadastroPautaRequestDTO dto) {
		Pauta pauta = repository.salvar(prepararPautaParaInclusao(dto));
		return mapper.pautaParaPautaResponseDTO(pauta);
	}
	
	public Pauta prepararPautaParaInclusao(final CadastroPautaRequestDTO dto) {
		Pauta pauta = mapper.cadastroPautaRequestDTOParaPauta(dto);
		pauta.setDataHoraInclusao(LocalDateTime.now());
		return pauta;
	}

}
