package br.tec.db.desafio_votacao.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class FiltroAssociadoVO {
	
	private final Long id;
	
	private final Long cpf;
	
	private final String nome;
	
	private final String email;
	
}
