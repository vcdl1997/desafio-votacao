package br.tec.db.desafio_votacao.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class FiltroPautaVO {
	
	private final Long id;
	
	private final String assunto;
	
}
