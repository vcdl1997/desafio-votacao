package br.tec.db.desafio_votacao.shared.utils;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes Unitários - Date Utils")
public class DateUtilsTest {

	@Test
    @DisplayName("Deve converter string válida para LocalDateTime sem segundos")
    void deveConverterStringValidaParaLocalDateTime() {
        String dataStr = "2025-07-11 15:45";
        LocalDateTime resultado = DateUtils.converteStringParaLocalDateTimeSemSegundos(dataStr);

        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado.getYear()).isEqualTo(2025);
        Assertions.assertThat(resultado.getMinute()).isEqualTo(45);
        Assertions.assertThat(resultado.getSecond()).isEqualTo(0); // truncado
    }

    @Test
    @DisplayName("Deve retornar null para string nula ou vazia")
    void deveRetornarNullParaStringVaziaOuNula() {
    	Assertions.assertThat(DateUtils.converteStringParaLocalDateTimeSemSegundos(null)).isNull();
    	Assertions.assertThat(DateUtils.converteStringParaLocalDateTimeSemSegundos("")).isNull();
    }

    @Test
    @DisplayName("Deve retornar null para string em formato inválido")
    void deveRetornarNullParaStringInvalida() {
    	Assertions.assertThat(DateUtils.converteStringParaLocalDateTimeSemSegundos("11/07/2025 15:45")).isNull();
    	Assertions.assertThat(DateUtils.converteStringParaLocalDateTimeSemSegundos("data errada")).isNull();
    }

    @Test
    @DisplayName("Deve converter LocalDateTime para string com formato correto")
    void deveConverterLocalDateTimeParaString() {
        LocalDateTime dataHora = LocalDateTime.of(2025, 7, 11, 15, 30);
        String resultado = DateUtils.converteLocalDateTimeParaString(dataHora);

        Assertions.assertThat(resultado).isEqualTo("2025-07-11 15:30");
    }

    @Test
    @DisplayName("Deve retornar null ao converter LocalDateTime nulo")
    void deveRetornarNullParaLocalDateTimeNulo() {
        Assertions.assertThat(DateUtils.converteLocalDateTimeParaString(null)).isNull();
    }
	
}
