package br.tec.db.desafio_votacao.appication.dto.commons.response;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.FieldError;

import br.tec.db.desafio_votacao.application.dto.commons.response.ResponseErrorDTO;

@DisplayName("Testes Unitários - Response Error")
public class ResponseErrorDTOTest {

	@Test
    @DisplayName("Deve criar ResponseErrorDTO com sucesso a partir de um FieldError")
    void deveCriarErroIndividualComSucesso() {
        FieldError fieldError = new FieldError("obj", "email", "Email inválido");

        ResponseErrorDTO dto = ResponseErrorDTO.criar(fieldError);

        Assertions.assertThat(dto).isNotNull();
        Assertions.assertThat(dto.getCampo()).isEqualTo("email");
        Assertions.assertThat(dto.getMensagem()).isEqualTo("Email inválido");
    }

    @Test
    @DisplayName("Deve criar lista de ResponseErrorDTO a partir de lista de FieldError")
    void deveCriarListaDeErrosComSucesso() {
        FieldError f1 = new FieldError("obj", "idSessaoVotacao", "idSessaoVotacao é obrigatório");
        FieldError f2 = new FieldError("obj", "idPauta", "idPauta inválida");

        List<ResponseErrorDTO> dtos = ResponseErrorDTO.criar(List.of(f1, f2));

        Assertions.assertThat(dtos).hasSize(2);
        Assertions.assertThat(dtos.get(0).getCampo()).isEqualTo("idSessaoVotacao");
        Assertions.assertThat(dtos.get(1).getCampo()).isEqualTo("idPauta");
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando lista de FieldError for nula ou vazia")
    void deveLancarExcecaoParaListaNulaOuVazia() {
        Assertions.assertThatThrownBy(() -> ResponseErrorDTO.criar(Collections.emptyList()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Lista de erros inválida: nula ou vazia.");
    }
	
}
