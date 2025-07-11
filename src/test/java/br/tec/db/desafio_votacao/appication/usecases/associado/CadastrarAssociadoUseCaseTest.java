package br.tec.db.desafio_votacao.appication.usecases.associado;

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

import br.tec.db.desafio_votacao.application.dto.associado.request.CadastroAssociadoRequestDTO;
import br.tec.db.desafio_votacao.application.dto.associado.response.AssociadoResponseDTO;
import br.tec.db.desafio_votacao.application.mappers.AssociadoMapper;
import br.tec.db.desafio_votacao.application.usecases.associado.CadastrarAssociadoUseCase;
import br.tec.db.desafio_votacao.domain.entities.Associado;
import br.tec.db.desafio_votacao.domain.repositories.AssociadoRepository;
import br.tec.db.desafio_votacao.shared.exceptions.BusinessException;

@DisplayName("Testes Unitários - Cadastrar Associado")
@ExtendWith(MockitoExtension.class)
public class CadastrarAssociadoUseCaseTest {

	@Mock
    private AssociadoRepository repository;

    @Mock
    private AssociadoMapper mapper;

    @InjectMocks
    private CadastrarAssociadoUseCase useCase;
    
    private final Faker faker = new Faker();

    @Test
    @DisplayName("Deve cadastrar um novo associado com sucesso quando CPF não estiver em uso")
    void deveCadastrarAssociadoComSucesso() {
        CadastroAssociadoRequestDTO dto = new CadastroAssociadoRequestDTO();
        dto.setCpf(faker.number().digits(11));
        dto.setNome(faker.name().fullName());
        dto.setEmail(faker.internet().emailAddress());
        
        
        Associado associado = new Associado();
        associado.setCpf(Long.valueOf(dto.getCpf()));
        associado.setNome(dto.getNome());
        associado.setEmail(dto.getEmail());

        Associado associadoSalvo = new Associado();
        associadoSalvo.setId(faker.number().randomNumber());
        associadoSalvo.setCpf(Long.valueOf(dto.getCpf()));
        associadoSalvo.setNome(dto.getNome());
        associadoSalvo.setEmail(dto.getEmail());
        associadoSalvo.setDataHoraInclusao(LocalDateTime.now());

        AssociadoResponseDTO response = AssociadoResponseDTO
        	.builder()
        	.id(associadoSalvo.getId())
        	.nome(dto.getNome())
        	.email(dto.getEmail())
        	.build()
        ;

        Mockito.when(mapper.cadastroAssociadoRequestDTOParaAssociado(dto)).thenReturn(associado);
        
        Mockito.when(repository.existemUsuariosComEsteCpf(Long.valueOf(dto.getCpf()))).thenReturn(false);
        
        Mockito.when(repository.salvar(associado)).thenReturn(associadoSalvo);
        
        Mockito.when(mapper.associadoParaAssociadoResponseDTO(associadoSalvo)).thenReturn(response);

        AssociadoResponseDTO result = useCase.executar(dto);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getNome()).isEqualTo(dto.getNome());
        Assertions.assertThat(result.getEmail()).isEqualTo(dto.getEmail());

        Mockito.verify(mapper).cadastroAssociadoRequestDTOParaAssociado(dto);
        Mockito.verify(repository).existemUsuariosComEsteCpf(Long.valueOf(dto.getCpf()));
        Mockito.verify(repository).salvar(associado);
        Mockito.verify(mapper).associadoParaAssociadoResponseDTO(associadoSalvo);
    }

    @Test
    @DisplayName("Deve lançar BusinessException ao tentar cadastrar CPF já existente")
    void deveLancarBusinessExceptionQuandoCpfJaExistir() {
    	CadastroAssociadoRequestDTO dto = new CadastroAssociadoRequestDTO();
        dto.setCpf(faker.number().digits(11));
        dto.setNome(faker.name().fullName());
        dto.setEmail(faker.internet().emailAddress());
        
        Associado associado = new Associado();
        associado.setNome(dto.getNome());
        associado.setEmail(dto.getEmail());
        associado.setCpf(Long.valueOf(dto.getCpf()));

        Mockito.when(mapper.cadastroAssociadoRequestDTOParaAssociado(dto)).thenReturn(associado);
        Mockito.when(repository.existemUsuariosComEsteCpf(Long.valueOf(dto.getCpf()))).thenReturn(true);

        Assertions.assertThatThrownBy(() -> useCase.executar(dto))
            .isInstanceOf(BusinessException.class)
            .hasMessage("Este CPF já está em uso por outro associado.");

        Mockito.verify(mapper).cadastroAssociadoRequestDTOParaAssociado(dto);
        Mockito.verify(repository).existemUsuariosComEsteCpf(Long.valueOf(dto.getCpf()));
        Mockito.verifyNoMoreInteractions(repository);
    }
	
}
