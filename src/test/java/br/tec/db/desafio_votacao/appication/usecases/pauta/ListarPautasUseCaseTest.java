package br.tec.db.desafio_votacao.appication.usecases.pauta;

import java.time.LocalDateTime;
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

import br.tec.db.desafio_votacao.application.dto.pauta.request.FiltroPautaRequestDTO;
import br.tec.db.desafio_votacao.application.dto.pauta.response.PautaResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.PautaMapper;
import br.tec.db.desafio_votacao.application.usecases.pauta.ListarPautasUseCase;
import br.tec.db.desafio_votacao.domain.entities.Pauta;
import br.tec.db.desafio_votacao.domain.repositories.PautaRepository;
import br.tec.db.desafio_votacao.domain.vo.FiltroPautaVO;

@DisplayName("Testes Unitários - Listar Pautas")
@ExtendWith(MockitoExtension.class)
public class ListarPautasUseCaseTest {
	
	@Mock
    private PautaRepository repository;

    @Mock
    private PautaMapper mapper;

    @InjectMocks
    private ListarPautasUseCase useCase;
    
    private final Faker faker = new Faker();

    @Test
    @DisplayName("Deve listar pautas com base no filtro e na paginação")
    void deveListarPautasComFiltroEPaginacao() {
    	
    	Long id = faker.number().randomNumber();
    	String assunto = faker.artist().name();
        FiltroPautaRequestDTO filtroDTO = new FiltroPautaRequestDTO(id.toString(), assunto);
        FiltroPautaVO filtroVO = new FiltroPautaVO(id, assunto);

        Pageable pageable = PageRequest.of(0, 10);

        Pauta pauta = new Pauta();
        pauta.setId(id);
        pauta.setAssunto(assunto);
        pauta.setDataHoraInclusao(LocalDateTime.now());

        PautaResponseDTO responseDTO = new PautaResponseDTO(pauta.getId(), pauta.getAssunto(), pauta.getDataHoraInclusao());

        Page<Pauta> resultadoConsulta = new PageImpl<>(List.of(pauta), pageable, 1);

        Mockito.when(mapper.filtroPautaRequestDTOParaFiltroPautaVO(filtroDTO)).thenReturn(filtroVO);
        Mockito.when(repository.listar(filtroVO, pageable)).thenReturn(resultadoConsulta);
        Mockito.when(mapper.pautaParaPautaResponseDTO(pauta)).thenReturn(responseDTO);

        Page<PautaResponseDTO> resultado = useCase.executar(filtroDTO, pageable);

        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado.getContent()).hasSize(1);
        Assertions.assertThat(resultado.getContent().get(0).getAssunto()).isEqualTo(assunto);

        Mockito.verify(mapper).filtroPautaRequestDTOParaFiltroPautaVO(filtroDTO);
        Mockito.verify(repository).listar(filtroVO, pageable);
        Mockito.verify(mapper).pautaParaPautaResponseDTO(pauta);
    }

}
