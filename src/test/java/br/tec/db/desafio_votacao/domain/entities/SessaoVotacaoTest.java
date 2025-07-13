package br.tec.db.desafio_votacao.domain.entities;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.tec.db.desafio_votacao.shared.exceptions.BusinessException;

@DisplayName("Testes Unitários - Entiade Sessão Votação")
public class SessaoVotacaoTest {

	@Test
    @DisplayName("Deve retornar true quando dataHoraEncerramento for futura (sessão em aberto)")
    void deveRetornarSessaoEmAberto() {
        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setDataHoraEncerramento(LocalDateTime.now().plusMinutes(2));

        Assertions.assertThat(sessao.isEmAberto()).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando dataHoraEncerramento for passada (sessão encerrada)")
    void deveRetornarSessaoEncerrada() {
        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setDataHoraEncerramento(LocalDateTime.now().minusMinutes(5));

        Assertions.assertThat(sessao.isEmAberto()).isFalse();
    }

    @Test
    @DisplayName("Deve retornar false quando dataHoraEncerramento for nula")
    void deveRetornarFalsoSeDataHoraEncerramentoForNula() {
        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setDataHoraEncerramento(null);

        Assertions.assertThat(sessao.isEmAberto()).isFalse();
    }

    @Test
    @DisplayName("Deve lançar exceção se dataHoraEncerramento estiver no passado")
    void deveLancarExcecaoSeDataHoraEncerramentoForPassada() {
        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setDataHoraEncerramento(LocalDateTime.now().minusMinutes(1));

        Assertions.assertThatThrownBy(sessao::validarRegrasDeNegocioParaInclusao)
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("data hora atual");
    }

    @Test
    @DisplayName("Não deve lançar exceção se dataHoraEncerramento for futura")
    void deveAceitarDataHoraEncerramentoValida() {
        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setDataHoraEncerramento(LocalDateTime.now().plusMinutes(2));

        Assertions.assertThatCode(sessao::validarRegrasDeNegocioParaInclusao)
            .doesNotThrowAnyException();
    }
	
}
