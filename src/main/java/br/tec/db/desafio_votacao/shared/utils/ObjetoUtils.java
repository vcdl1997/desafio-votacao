package br.tec.db.desafio_votacao.shared.utils;

import java.util.Arrays;
import java.util.Objects;

public class ObjetoUtils {

	public static void requireNonNull(String mensagem, Object... valores) {
		Objects.requireNonNull(mensagem, "Mensagem de erro não informada");
		Arrays.asList(valores).stream().forEach(valor -> Objects.requireNonNull(valor));
	}
	
}
