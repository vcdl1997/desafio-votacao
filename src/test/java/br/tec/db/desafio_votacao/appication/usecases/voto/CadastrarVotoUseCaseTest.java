package br.tec.db.desafio_votacao.appication.usecases.voto;

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

import br.tec.db.desafio_votacao.application.dto.voto.request.VotoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.voto.response.VotoResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.VotoMapper;
import br.tec.db.desafio_votacao.application.usecases.voto.CadastrarVotoUseCase;
import br.tec.db.desafio_votacao.domain.entities.Associado;
import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;
import br.tec.db.desafio_votacao.domain.entities.Voto;
import br.tec.db.desafio_votacao.domain.entities.VotoId;
import br.tec.db.desafio_votacao.domain.repositories.AssociadoRepository;
import br.tec.db.desafio_votacao.domain.repositories.SessaoVotacaoRepository;
import br.tec.db.desafio_votacao.domain.repositories.VotoRepository;
import br.tec.db.desafio_votacao.shared.exceptions.BusinessException;
import br.tec.db.desafio_votacao.shared.exceptions.NotFoundException;

@DisplayName("Testes Unitários - Cadastrar Voto")
@ExtendWith(MockitoExtension.class)
public class CadastrarVotoUseCaseTest {

	@Mock
    private AssociadoRepository associadoRepository;

    @Mock
    private SessaoVotacaoRepository sessaoVotacaoRepository;

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private VotoMapper votoMapper;

    @InjectMocks
    private CadastrarVotoUseCase useCase;

    @Test
    @DisplayName("Deve cadastrar voto com sucesso")
    void deveCadastrarVotoComSucesso() {
        VotoRequestDTO dto = new VotoRequestDTO("2", "1", "SIM");

        Associado associado = new Associado();
        associado.setId(1L);

        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setId(2L);
        sessao.setDataHoraEncerramento(LocalDateTime.now().plusMinutes(5));

        VotoId votoId = VotoId.builder().associado(associado).sessao(sessao).build();

        Voto voto = new Voto();
        voto.setId(votoId);
        voto.setDataHoraInclusao(LocalDateTime.now());

        Mockito.when(associadoRepository.obterPorId(1L)).thenReturn(Optional.of(associado));
        Mockito.when(sessaoVotacaoRepository.obterPorId(2L)).thenReturn(Optional.of(sessao));
        Mockito.when(votoMapper.votoRequestDTOParaVoto(dto)).thenReturn(voto);
        Mockito.when(votoRepository.associadoJaVotouNessaSessaoVotacao(votoId)).thenReturn(false);
        Mockito.when(votoRepository.salvar(voto)).thenReturn(voto);

        VotoResponseDTO response = useCase.executar(dto);

        Assertions.assertThat(response).isNotNull();
        Mockito.verify(votoRepository).salvar(voto);
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando associado já votou")
    void deveLancarExcecaoQuandoAssociadoJaVotou() {
        VotoRequestDTO dto = new VotoRequestDTO("2", "1", "SIM");
        Associado associado = new Associado();
        associado.setId(1L);

        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setId(2L);
        sessao.setDataHoraEncerramento(LocalDateTime.now().plusMinutes(5));

        VotoId votoId = VotoId.builder().associado(associado).sessao(sessao).build();
        Voto voto = new Voto();
        voto.setId(votoId);

        Mockito.when(associadoRepository.obterPorId(1L)).thenReturn(Optional.of(associado));
        Mockito.when(sessaoVotacaoRepository.obterPorId(2L)).thenReturn(Optional.of(sessao));
        Mockito.when(votoMapper.votoRequestDTOParaVoto(dto)).thenReturn(voto);
        Mockito.when(votoRepository.associadoJaVotouNessaSessaoVotacao(votoId)).thenReturn(true);

        Assertions.assertThatThrownBy(() -> useCase.executar(dto))
            .isInstanceOf(BusinessException.class)
            .hasMessage("O associado informado já votou nesta sessão de votação.");
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando sessão está encerrada")
    void deveLancarExcecaoQuandoSessaoEncerrada() {
        VotoRequestDTO dto = new VotoRequestDTO("2", "1", "SIM");
        Associado associado = new Associado();
        associado.setId(1L);

        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setId(2L);
        sessao.setDataHoraEncerramento(LocalDateTime.now().minusHours(1));

        VotoId votoId = VotoId.builder().associado(associado).sessao(sessao).build();
        Voto voto = new Voto();
        voto.setId(votoId);

        Mockito.when(associadoRepository.obterPorId(1L)).thenReturn(Optional.of(associado));
        Mockito.when(sessaoVotacaoRepository.obterPorId(2L)).thenReturn(Optional.of(sessao));
        Mockito.when(votoMapper.votoRequestDTOParaVoto(dto)).thenReturn(voto);
        Mockito.when(votoRepository.associadoJaVotouNessaSessaoVotacao(votoId)).thenReturn(false);

        Assertions.assertThatThrownBy(() -> useCase.executar(dto))
            .isInstanceOf(BusinessException.class)
            .hasMessage("Esta sessão de votação já foi encerrada e não pode mais receber votos.");
    }

    @Test
    @DisplayName("Deve lançar NotFoundException se associado não for encontrado")
    void deveLancarExcecaoQuandoAssociadoNaoEncontrado() {
        VotoRequestDTO dto = new VotoRequestDTO("2", "1", "SIM");
        Mockito.when(associadoRepository.obterPorId(1L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> useCase.executar(dto))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("Associado não encontrado");
    }

    @Test
    @DisplayName("Deve lançar NotFoundException se sessão de votação não for encontrada")
    void deveLancarExcecaoQuandoSessaoNaoEncontrada() {
        VotoRequestDTO dto = new VotoRequestDTO("2", "1", "SIM");
        Associado associado = new Associado();
        associado.setId(1L);

        Mockito.when(associadoRepository.obterPorId(1L)).thenReturn(Optional.of(associado));
        Mockito.when(sessaoVotacaoRepository.obterPorId(2L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> useCase.executar(dto))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("Sessão Votação não encontrada");
    }
	
}
