package br.tec.db.desafio_votacao.appication.usecases.sessaovotacao;

import java.time.LocalDateTime;
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

import br.tec.db.desafio_votacao.application.dto.sessaovotacao.request.CadastroSessaoVotacaoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.sessaovotacao.response.SessaoVotacaoResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.SessaoVotacaoMapper;
import br.tec.db.desafio_votacao.application.usecases.sessaovotacao.CadastrarSessaoVotacaoUseCase;
import br.tec.db.desafio_votacao.domain.entities.Pauta;
import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;
import br.tec.db.desafio_votacao.domain.repositories.PautaRepository;
import br.tec.db.desafio_votacao.domain.repositories.SessaoVotacaoRepository;
import br.tec.db.desafio_votacao.shared.exceptions.BusinessException;
import br.tec.db.desafio_votacao.shared.exceptions.NotFoundException;
import br.tec.db.desafio_votacao.shared.utils.DateUtils;

@DisplayName("Testes Unitários - Cadastrar Sessão de Votação")
@ExtendWith(MockitoExtension.class)
public class CadastrarSessaoVotacaoUseCaseTest {

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private SessaoVotacaoRepository sessaoVotacaoRepository;

    @Mock
    private SessaoVotacaoMapper sessaoVotacaoMapper;

    @InjectMocks
    private CadastrarSessaoVotacaoUseCase useCase;

    private final Faker faker = new Faker();

    @Test
    @DisplayName("Deve cadastrar uma sessão de votação com sucesso quando não houver conflitos")
    void deveCadastrarSessaoComSucesso() {
        Long idPauta = faker.number().randomNumber();
        LocalDateTime dataHoraEncerramento = LocalDateTime.now().plusHours(1);
        String dataHoraEncerramentoStr = DateUtils.converteLocalDateTimeParaString(dataHoraEncerramento);
    	CadastroSessaoVotacaoRequestDTO dto = new CadastroSessaoVotacaoRequestDTO(idPauta.toString(), dataHoraEncerramentoStr);
        Pauta pauta = new Pauta();
        pauta.setId(idPauta);

        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setPauta(pauta);
        sessao.setDataHoraEncerramento(dataHoraEncerramento);

        Mockito.when(pautaRepository.obterPorId(idPauta)).thenReturn(Optional.of(pauta));
        Mockito.when(sessaoVotacaoMapper.cadastroSessaoVotacaoRequestDTOParaSessaoVotacao(dto)).thenReturn(sessao);
        Mockito.when(sessaoVotacaoRepository.existemSessoesAbertasParaEssaPauta(
        	ArgumentMatchers.any(), ArgumentMatchers.any(),  ArgumentMatchers.eq(idPauta))).thenReturn(false);
        Mockito.when(sessaoVotacaoRepository.salvar(sessao)).thenReturn(sessao);


        SessaoVotacaoResponseDTO response = useCase.cadastrar(dto);

        Assertions.assertThat(response).isNotNull();
        Mockito.verify(pautaRepository).obterPorId(idPauta);
        Mockito.verify(sessaoVotacaoRepository).salvar(sessao);
    }

    @Test
    @DisplayName("Deve lançar BusinessException ao tentar cadastrar sessão com outra sessão já aberta")
    void deveLancarBusinessExceptionQuandoSessaoJaEstiverAberta() {
    	Long idPauta = faker.number().randomNumber();
        LocalDateTime dataHoraFim = LocalDateTime.now().plusMinutes(30);
        String dataHoraFimStr = DateUtils.converteLocalDateTimeParaString(dataHoraFim);
    	
        CadastroSessaoVotacaoRequestDTO dto = new CadastroSessaoVotacaoRequestDTO(idPauta.toString(), dataHoraFimStr);
        Pauta pauta = new Pauta();
        pauta.setId(idPauta);

        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setPauta(pauta);
        sessao.setDataHoraEncerramento(dataHoraFim);

        Mockito.when(pautaRepository.obterPorId(idPauta)).thenReturn(Optional.of(pauta));
        Mockito.when(sessaoVotacaoMapper.cadastroSessaoVotacaoRequestDTOParaSessaoVotacao(dto)).thenReturn(sessao);
        Mockito.when(sessaoVotacaoRepository.existemSessoesAbertasParaEssaPauta(
        	ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.eq(idPauta))).thenReturn(true);

        Assertions.assertThatThrownBy(() -> useCase.cadastrar(dto))
            .isInstanceOf(BusinessException.class)
            .hasMessage("A pauta já possui uma sessão de votação em andamento.");
    }

    @Test
    @DisplayName("Deve lançar NotFoundException quando a pauta não existir")
    void deveLancarNotFoundExceptionQuandoPautaNaoExistir() {
    	Long idPauta = faker.number().randomNumber();
        LocalDateTime dataHoraFim = LocalDateTime.now().plusMinutes(30);
        String dataHoraFimStr = DateUtils.converteLocalDateTimeParaString(dataHoraFim);
    	
        CadastroSessaoVotacaoRequestDTO dto = new CadastroSessaoVotacaoRequestDTO(idPauta.toString(), dataHoraFimStr);
        Mockito.when(pautaRepository.obterPorId(idPauta)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> useCase.cadastrar(dto))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("Pauta não encontrada");
    }
	
}
