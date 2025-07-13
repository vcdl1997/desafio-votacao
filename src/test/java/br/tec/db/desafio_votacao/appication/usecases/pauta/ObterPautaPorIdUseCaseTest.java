package br.tec.db.desafio_votacao.appication.usecases.pauta;

import java.time.LocalDateTime;
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

import br.tec.db.desafio_votacao.application.dto.pauta.response.PautaResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.PautaMapper;
import br.tec.db.desafio_votacao.application.usecases.pauta.ObterPautaPorIdUseCase;
import br.tec.db.desafio_votacao.domain.entities.Pauta;
import br.tec.db.desafio_votacao.domain.repositories.PautaRepository;
import br.tec.db.desafio_votacao.shared.exceptions.NotFoundException;

@DisplayName("Testes Unitários - Obter Pauta por ID")
@ExtendWith(MockitoExtension.class)
public class ObterPautaPorIdUseCaseTest {
	
	@Mock
    private PautaRepository repository;

    @Mock
    private PautaMapper mapper;

    @InjectMocks
    private ObterPautaPorIdUseCase useCase;

    private final Faker faker = new Faker();

    @Test
    @DisplayName("Deve retornar pauta com sucesso quando ID existir")
    void deveRetornarPautaComSucesso() {
        Long id = faker.number().randomNumber();
        String assunto = faker.artist().name();
        Pauta pauta = new Pauta();
        pauta.setId(id);
        pauta.setAssunto(assunto);

        PautaResponseDTO responseDTO = new PautaResponseDTO(id, assunto, LocalDateTime.now());

        Mockito.when(repository.obterPorId(id)).thenReturn(Optional.of(pauta));
        Mockito.when(mapper.pautaParaPautaResponseDTO(pauta)).thenReturn(responseDTO);

        PautaResponseDTO resultado = useCase.executar(id);

        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado.getId()).isEqualTo(id);
        Assertions.assertThat(resultado.getAssunto()).isEqualTo(assunto);

        Mockito.verify(repository).obterPorId(id);
        Mockito.verify(mapper).pautaParaPautaResponseDTO(pauta);
    }

    @Test
    @DisplayName("Deve lançar NotFoundException quando pauta não for encontrada")
    void deveLancarNotFoundExceptionQuandoPautaNaoExistir() {
        Long id = faker.number().randomNumber();

        Mockito.when(repository.obterPorId(id)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> useCase.executar(id))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("Pauta não encontrada");

        Mockito.verify(repository).obterPorId(id);
    }

}
