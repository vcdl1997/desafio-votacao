package br.tec.db.desafio_votacao.appication.usecases.sessaovotacao;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;

import br.tec.db.desafio_votacao.application.dto.sessaovotacao.response.SessaoVotacaoResponseDTO;
import br.tec.db.desafio_votacao.application.usecases.sessaovotacao.ObterSessaoVotacaoPorIdUseCase;
import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;
import br.tec.db.desafio_votacao.domain.repositories.SessaoVotacaoRepository;
import br.tec.db.desafio_votacao.shared.exceptions.NotFoundException;

@DisplayName("Testes Unitários - Obter Sessão Votação por ID")
@ExtendWith(MockitoExtension.class)
public class ObterSessaoVotacaoPorIdUseCaseTest {

	@Mock
    private SessaoVotacaoRepository repository;

    @InjectMocks
    private ObterSessaoVotacaoPorIdUseCase useCase;
    
    private final Faker faker = new Faker();

    @Test
    @DisplayName("Deve retornar sessão de votação quando ID existir")
    void deveRetornarSessaoVotacaoComSucesso() {
        Long id = faker.number().randomNumber();
        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setId(id);

        Mockito.when(repository.obterPorId(id)).thenReturn(Optional.of(sessao));

        SessaoVotacaoResponseDTO resultado = useCase.executar(id);

        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado.getId()).isEqualTo(id);

        Mockito.verify(repository).obterPorId(id);
    }

    @Test
    @DisplayName("Deve lançar NotFoundException quando sessão não existir")
    void deveLancarNotFoundExceptionQuandoSessaoNaoExistir() {
        Long id = faker.number().randomNumber();
        
        Mockito.when(repository.obterPorId(id)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> useCase.executar(id))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("Sessão Votação não encontrada");

        Mockito.verify(repository).obterPorId(id);
    }	
	
}
