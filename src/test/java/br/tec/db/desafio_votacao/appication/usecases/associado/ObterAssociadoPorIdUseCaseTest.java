package br.tec.db.desafio_votacao.appication.usecases.associado;

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

import br.tec.db.desafio_votacao.application.dto.associado.response.AssociadoResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.AssociadoMapper;
import br.tec.db.desafio_votacao.application.usecases.associado.ObterAssociadoPorIdUseCase;
import br.tec.db.desafio_votacao.domain.entities.Associado;
import br.tec.db.desafio_votacao.domain.repositories.AssociadoRepository;
import br.tec.db.desafio_votacao.shared.exceptions.NotFoundException;

@DisplayName("Testes Unitários - Obter Associado por ID")
@ExtendWith(MockitoExtension.class)
public class ObterAssociadoPorIdUseCaseTest {

	@Mock
    private AssociadoRepository repository;

    @Mock
    private AssociadoMapper mapper;

    @InjectMocks
    private ObterAssociadoPorIdUseCase useCase;
    
    private final Faker faker = new Faker();

    @Test
    @DisplayName("Deve retornar associado com sucesso quando ID existir")
    void deveRetornarAssociadoComSucesso() {
    	Associado associado = new Associado();
        associado.setId(faker.number().randomNumber());
        associado.setNome(faker.name().fullName());
        associado.setEmail(faker.internet().emailAddress());

        AssociadoResponseDTO responseDTO = AssociadoResponseDTO
        	.builder()
        	.id(associado.getId())
        	.nome(associado.getNome())
        	.email(associado.getEmail())
        	.build()
        ;

        Mockito.when(repository.obterPorId(associado.getId())).thenReturn(Optional.of(associado));
        Mockito.when(mapper.associadoParaAssociadoResponseDTO(associado)).thenReturn(responseDTO);

        AssociadoResponseDTO result = useCase.executar(associado.getId());

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getNome()).isEqualTo(associado.getNome());
        Assertions.assertThat(result.getEmail()).isEqualTo(associado.getEmail());

        Mockito.verify(repository).obterPorId(associado.getId());
        Mockito.verify(mapper).associadoParaAssociadoResponseDTO(associado);
    }

    @Test
    @DisplayName("Deve lançar NotFoundException quando associado não for encontrado")
    void deveLancarNotFoundExceptionQuandoAssociadoNaoExistir() {
        Long id = faker.number().randomNumber();

        Mockito.when(repository.obterPorId(id)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> useCase.executar(id))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("Associado não encontrado");

        Mockito.verify(repository).obterPorId(id);
    }
	
}
