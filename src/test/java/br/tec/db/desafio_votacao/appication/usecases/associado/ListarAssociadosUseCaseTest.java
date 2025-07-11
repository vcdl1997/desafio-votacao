package br.tec.db.desafio_votacao.appication.usecases.associado;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.github.javafaker.Faker;

import br.tec.db.desafio_votacao.application.dto.associado.request.FiltroAssociadoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.associado.response.AssociadoResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.AssociadoMapper;
import br.tec.db.desafio_votacao.application.usecases.associado.ListarAssociadosUseCase;
import br.tec.db.desafio_votacao.domain.entities.Associado;
import br.tec.db.desafio_votacao.domain.repositories.AssociadoRepository;
import br.tec.db.desafio_votacao.domain.vo.FiltroAssociadoVO;

@DisplayName("Testes Unitários - Listar Associados")
@ExtendWith(MockitoExtension.class)
public class ListarAssociadosUseCaseTest {

	@Mock
    private AssociadoRepository repository;

    @Mock
    private AssociadoMapper mapper;

    @InjectMocks
    private ListarAssociadosUseCase useCase;

    private final Faker faker = new Faker();

    @Test
    @DisplayName("Deve listar associados com base no filtro e na paginação")
    void deveListarAssociadosComFiltroEPaginacao() {
        FiltroAssociadoRequestDTO filtroDTO = new FiltroAssociadoRequestDTO();
        FiltroAssociadoVO filtroVO = new FiltroAssociadoVO(null, null, null, null);

        Pageable pageable = PageRequest.of(0, 10);

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

        Page<Associado> resultadoConsulta = new PageImpl<>(List.of(associado), pageable, 1);

        Mockito.when(mapper.filtroAssociadoDTOParaFiltroAssociadoVO(filtroDTO)).thenReturn(filtroVO);
        Mockito.when(repository.listar(filtroVO, pageable)).thenReturn(resultadoConsulta);
        Mockito.when(mapper.associadoParaAssociadoResponseDTO(associado)).thenReturn(responseDTO);

        Page<AssociadoResponseDTO> resultado = useCase.executar(filtroDTO, pageable);

        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado.getContent()).hasSize(1);
        Assertions.assertThat(resultado.getContent().get(0).getNome()).isEqualTo(associado.getNome());

        Mockito.verify(mapper).filtroAssociadoDTOParaFiltroAssociadoVO(filtroDTO);
        Mockito.verify(repository).listar(filtroVO, pageable);
        Mockito.verify(mapper).associadoParaAssociadoResponseDTO(associado);
    }
	
}
