package br.tec.db.desafio_votacao.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class FiltroSessaoVotacaoVO {
	
	private final Long idSessao;
	
	private final Long idPauta;
	
}
