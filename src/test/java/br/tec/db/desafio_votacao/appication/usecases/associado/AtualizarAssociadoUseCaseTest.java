package br.tec.db.desafio_votacao.appication.usecases.associado;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;

import br.tec.db.desafio_votacao.application.dto.associado.request.AtualizacaoAssociadoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.associado.response.AssociadoResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.AssociadoMapper;
import br.tec.db.desafio_votacao.application.usecases.associado.AtualizarAssociadoUseCase;
import br.tec.db.desafio_votacao.domain.entities.Associado;
import br.tec.db.desafio_votacao.domain.repositories.AssociadoRepository;
import br.tec.db.desafio_votacao.shared.exceptions.NotFoundException;


@DisplayName("Testes Unitários - Atualizar Associado")
@ExtendWith(MockitoExtension.class)
public class AtualizarAssociadoUseCaseTest {

	@Mock
	private AssociadoRepository repository;

    @Mock
    private AssociadoMapper mapper;

    @InjectMocks
    private AtualizarAssociadoUseCase useCase;
    
    private final Faker faker = new Faker();

	@Test
	@DisplayName("Deve atualizar um associado com sucesso quando ele existir no banco")
	void deveAtualizarAssociadoComSucesso() {
		Long id = faker.number().randomNumber();
	    AtualizacaoAssociadoRequestDTO dto = new AtualizacaoAssociadoRequestDTO(faker.name().fullName(), faker.internet().emailAddress());
	    Associado associado = Associado
	    	.builder()
	    	.id(id)
	    	.nome(faker.name().fullName())
	    	.email(faker.internet().emailAddress())
	    	.build()
	    ;

	    Mockito.when(repository.obterPorId(id)).thenReturn(Optional.of(associado));
	    Mockito.when(mapper.associadoParaAssociadoResponseDTO(ArgumentMatchers.any(Associado.class)))
	            .thenReturn(AssociadoResponseDTO.builder().id(id).nome(dto.getNome()).email(dto.getEmail()).build());

	    AssociadoResponseDTO response = useCase.executar(id, dto);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getNome()).isEqualTo(dto.getNome());
        Assertions.assertThat(response.getEmail()).isEqualTo(dto.getEmail());

        Mockito.verify(repository).obterPorId(id);
        Mockito.verify(mapper).associadoParaAssociadoResponseDTO(associado);
	}

    @Test
    @DisplayName("Deve lançar NotFoundException ao tentar atualizar um associado inexistente")
    void deveLancarNotFoundExceptionQuandoAssociadoNaoExistir() {
        Long id = 999L;
        AtualizacaoAssociadoRequestDTO dto = new AtualizacaoAssociadoRequestDTO(faker.name().fullName(), faker.internet().emailAddress());

        Mockito.when(repository.obterPorId(id)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> useCase.executar(id, dto))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Associado não encontrado");

        Mockito.verify(repository).obterPorId(id);
    }
	
}
