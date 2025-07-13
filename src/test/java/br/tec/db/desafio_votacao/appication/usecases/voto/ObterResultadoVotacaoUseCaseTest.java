package br.tec.db.desafio_votacao.appication.usecases.voto;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.tec.db.desafio_votacao.application.dto.voto.request.FiltroVotoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.voto.response.VotoResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.VotoMapper;
import br.tec.db.desafio_votacao.application.usecases.voto.ObterResultadoVotacaoUseCase;
import br.tec.db.desafio_votacao.domain.entities.Pauta;
import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;
import br.tec.db.desafio_votacao.domain.entities.Voto;
import br.tec.db.desafio_votacao.domain.enums.RespostaVotoEnum;
import br.tec.db.desafio_votacao.domain.repositories.SessaoVotacaoRepository;
import br.tec.db.desafio_votacao.domain.repositories.VotoRepository;
import br.tec.db.desafio_votacao.domain.vo.FiltroVotoVO;
import br.tec.db.desafio_votacao.shared.exceptions.BusinessException;
import br.tec.db.desafio_votacao.shared.exceptions.NotFoundException;

@DisplayName("Testes Unitários - Obter Resultado da Votação")
@ExtendWith(MockitoExtension.class)
public class ObterResultadoVotacaoUseCaseTest {

	@Mock
    private SessaoVotacaoRepository sessaoVotacaoRepository;

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private VotoMapper mapper;

    @InjectMocks
    private ObterResultadoVotacaoUseCase useCase;

    @Test
    @DisplayName("Deve retornar o resultado da votação com sucesso")
    void deveRetornarResultadoComSucesso() {
        FiltroVotoRequestDTO dto = new FiltroVotoRequestDTO("1");
        FiltroVotoVO vo = new FiltroVotoVO(1L);

        Pauta pauta = new Pauta();
        pauta.setAssunto("Assunto importante");

        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setId(1L);
        sessao.setPauta(pauta);
        sessao.setDataHoraEncerramento(java.time.LocalDateTime.now().minusMinutes(1));

        Voto voto1 = new Voto();
        voto1.setResposta(RespostaVotoEnum.SIM);
        Voto voto2 = new Voto();
        voto2.setResposta(RespostaVotoEnum.NAO);
        Voto voto3 = new Voto();
        voto3.setResposta(RespostaVotoEnum.SIM);

        List<Voto> votos = List.of(voto1, voto2, voto3);

        Mockito.when(mapper.filtroVotoRequestDTOParaFiltroVotoVO(dto)).thenReturn(vo);
        Mockito.when(sessaoVotacaoRepository.obterPorId(1L)).thenReturn(Optional.of(sessao));
        Mockito.when(votoRepository.listarVotosPorSessaoVotacao(vo)).thenReturn(votos);

        VotoResponseDTO resultado = useCase.executar(dto);

        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado.getPauta()).isEqualTo("Assunto importante");
        Assertions.assertThat(resultado.getResultado()).hasSize(2);

        var sim = resultado.getResultado().stream().filter(r -> r.getResposta() == RespostaVotoEnum.SIM.obterDescricao()).findFirst().orElse(null);
        var nao = resultado.getResultado().stream().filter(r -> r.getResposta() == RespostaVotoEnum.NAO.obterDescricao()).findFirst().orElse(null);

        Assertions.assertThat(sim).isNotNull();
        Assertions.assertThat(nao).isNotNull();
        Assertions.assertThat(sim.getQuantidade()).isEqualTo(2);
        Assertions.assertThat(nao.getQuantidade()).isEqualTo(1);
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando sessão ainda estiver em andamento")
    void deveLancarExcecaoQuandoSessaoEstiverAberta() {
        FiltroVotoRequestDTO dto = new FiltroVotoRequestDTO("1");
        FiltroVotoVO vo = new FiltroVotoVO(1L);

        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setId(1L);
        sessao.setDataHoraEncerramento(java.time.LocalDateTime.now().plusMinutes(10));

        Mockito.when(mapper.filtroVotoRequestDTOParaFiltroVotoVO(dto)).thenReturn(vo);
        Mockito.when(sessaoVotacaoRepository.obterPorId(1L)).thenReturn(Optional.of(sessao));

        Assertions.assertThatThrownBy(() -> useCase.executar(dto))
            .isInstanceOf(BusinessException.class)
            .hasMessage("Não é possível obter o resultado da votação, pois a sessão ainda se encontra em andamento");
    }

    @Test
    @DisplayName("Deve lançar NotFoundException quando sessão não for encontrada")
    void deveLancarNotFoundExceptionQuandoSessaoNaoEncontrada() {
        FiltroVotoRequestDTO dto = new FiltroVotoRequestDTO("1");
        FiltroVotoVO vo = new FiltroVotoVO(1L);

        Mockito.when(mapper.filtroVotoRequestDTOParaFiltroVotoVO(dto)).thenReturn(vo);
        Mockito.when(sessaoVotacaoRepository.obterPorId(1L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> useCase.executar(dto))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("Sessão Votação não encontrada");
    }
	
}
