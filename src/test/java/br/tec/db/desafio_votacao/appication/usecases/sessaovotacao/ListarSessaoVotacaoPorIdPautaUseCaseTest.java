package br.tec.db.desafio_votacao.appication.usecases.sessaovotacao;

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

import br.tec.db.desafio_votacao.application.dto.sessaovotacao.request.FiltroSessaoVotacaoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.sessaovotacao.response.SessaoVotacaoResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.SessaoVotacaoMapper;
import br.tec.db.desafio_votacao.application.usecases.sessaovotacao.ListarSessaoVotacaoPorIdPautaUseCase;
import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;
import br.tec.db.desafio_votacao.domain.repositories.SessaoVotacaoRepository;
import br.tec.db.desafio_votacao.domain.vo.FiltroSessaoVotacaoVO;

@DisplayName("Testes Unitários - Listar Sessões de Votação")
@ExtendWith(MockitoExtension.class)
public class ListarSessaoVotacaoPorIdPautaUseCaseTest {

	@Mock
    private SessaoVotacaoRepository repository;

    @Mock
    private SessaoVotacaoMapper mapper;

    @InjectMocks
    private ListarSessaoVotacaoPorIdPautaUseCase useCase;
    
    private final Faker faker = new Faker();
    
    @Test
    @DisplayName("Deve listar sessões de votação com base no filtro e na paginação")
    void deveListarSessoesComFiltroEPaginacao() {
    	Long idSessaoVotacao = faker.number().randomNumber();
    	Long idPauta = faker.number().randomNumber();
    	
        FiltroSessaoVotacaoRequestDTO filtroDTO = new FiltroSessaoVotacaoRequestDTO(idSessaoVotacao.toString(), idPauta.toString());
        FiltroSessaoVotacaoVO filtroVO = new FiltroSessaoVotacaoVO(idSessaoVotacao, idPauta);

        Pageable pageable = PageRequest.of(0, 5);

        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setId(idSessaoVotacao);

        Page<SessaoVotacao> resultadoConsulta = new PageImpl<>(List.of(sessao), pageable, 1);

        Mockito.when(mapper.filtroSessaoVotacaoRequestDTOParaFiltroSessaoVotacaoVO(filtroDTO)).thenReturn(filtroVO);
        Mockito.when(repository.listar(filtroVO, pageable)).thenReturn(resultadoConsulta);

        Page<SessaoVotacaoResponseDTO> resultado = useCase.listar(filtroDTO, pageable);

        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado.getContent()).hasSize(1);
        Assertions.assertThat(resultado.getContent().get(0).getId()).isEqualTo(sessao.getId());

        Mockito.verify(mapper).filtroSessaoVotacaoRequestDTOParaFiltroSessaoVotacaoVO(filtroDTO);
        Mockito.verify(repository).listar(filtroVO, pageable);
    }
	
}
