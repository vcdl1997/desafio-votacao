package br.tec.db.desafio_votacao.domain.enums;

import java.util.Arrays;
import java.util.Objects;

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
	
	public String obterDescricao() throws IllegalArgumentException {
        return Objects.equals(this, RespostaVotoEnum.SIM) ? "Sim" : "Não";
    }
}
