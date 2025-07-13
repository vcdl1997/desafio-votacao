package br.tec.db.desafio_votacao.appication.usecases.pauta;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;

import br.tec.db.desafio_votacao.application.dto.pauta.request.CadastroPautaRequestDTO;
import br.tec.db.desafio_votacao.application.dto.pauta.response.PautaResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.PautaMapper;
import br.tec.db.desafio_votacao.application.usecases.pauta.CadastrarPautaUseCase;
import br.tec.db.desafio_votacao.domain.entities.Pauta;
import br.tec.db.desafio_votacao.domain.repositories.PautaRepository;

@DisplayName("Testes Unitários - Cadastrar Pauta")
@ExtendWith(MockitoExtension.class)
public class CadastrarPautaUseCaseTest {
	
	@Mock
    private PautaRepository repository;

    @Mock
    private PautaMapper mapper;

    @InjectMocks
    private CadastrarPautaUseCase useCase;
    
    private final Faker faker = new Faker();
    
    @Test
    @DisplayName("Deve cadastrar uma nova pauta com sucesso")
    void deveCadastrarPautaComSucesso() {
        // Arrange
        CadastroPautaRequestDTO dto = new CadastroPautaRequestDTO(faker.artist().name());
        Pauta pauta = new Pauta();
        pauta.setAssunto(dto.getAssunto());

        Pauta pautaSalva = new Pauta();
        pautaSalva.setId(faker.number().randomNumber());
        pautaSalva.setAssunto(dto.getAssunto());
        pautaSalva.setDataHoraInclusao(LocalDateTime.now());

        PautaResponseDTO responseDTO = PautaResponseDTO
        	.builder()
        	.id(pautaSalva.getId())
        	.assunto(pautaSalva.getAssunto())
        	.dataHoraInclusao(pautaSalva.getDataHoraInclusao())
        	.build()
        ;

        Mockito.when(mapper.cadastroPautaRequestDTOParaPauta(dto)).thenReturn(pauta);
        Mockito.when(repository.salvar(pauta)).thenReturn(pautaSalva);
        Mockito.when(mapper.pautaParaPautaResponseDTO(pautaSalva)).thenReturn(responseDTO);

        PautaResponseDTO resultado = useCase.executar(dto);

        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado.getId()).isEqualTo(pautaSalva.getId());
        Assertions.assertThat(resultado.getAssunto()).isEqualTo(pautaSalva.getAssunto());

        Mockito.verify(mapper).cadastroPautaRequestDTOParaPauta(dto);
        Mockito.verify(repository).salvar(pauta);
        Mockito.verify(mapper).pautaParaPautaResponseDTO(pautaSalva);
    }

}
