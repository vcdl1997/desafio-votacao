package br.tec.db.desafio_votacao.shared.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes Unitários - Objeto Utils")
public class ObjetoUtilsTest {

	@Test
    @DisplayName("Deve passar sem exceção quando todos os valores são não nulos")
    void deveValidarComSucesso() {
		Assertions.assertThatCode(() -> ObjetoUtils.requireNonNull("Campos obrigatórios", "valor", 123, new Object()))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve lançar NullPointerException se a mensagem for nula")
    void deveLancarExcecaoSeMensagemForNula() {
    	Assertions.assertThatThrownBy(() -> ObjetoUtils.requireNonNull(null, "teste"))
            .isInstanceOf(NullPointerException.class)
            .hasMessage("Mensagem de erro não pode ser nulo");
    }

    @Test
    @DisplayName("Deve lançar NullPointerException se algum valor for nulo")
    void deveLancarExcecaoSeAlgumValorForNulo() {
        String nome = "teste";
        Integer idade = null;

        Assertions.assertThatThrownBy(() -> ObjetoUtils.requireNonNull("Campos obrigatórios", nome, idade))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Deve lançar NullPointerException se todos os valores forem nulos")
    void deveLancarExcecaoSeTodosOsValoresForemNulos() {
        Assertions.assertThatThrownBy(() -> ObjetoUtils.requireNonNull("Erro", null, null))
            .isInstanceOf(NullPointerException.class);
    }
	
}
