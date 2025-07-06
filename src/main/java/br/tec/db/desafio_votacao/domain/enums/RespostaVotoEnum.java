package br.tec.db.desafio_votacao.domain.enums;

import java.util.Arrays;

public enum RespostaVotoEnum {
	SIM,
    NAO;
	
	public static RespostaVotoEnum from(final String resposta) throws IllegalArgumentException {
        return Arrays.asList(values())
        	.stream()
        	.filter(e -> e.name().equalsIgnoreCase(resposta))
        	.findFirst()
        	.orElse(null);
    }
}
