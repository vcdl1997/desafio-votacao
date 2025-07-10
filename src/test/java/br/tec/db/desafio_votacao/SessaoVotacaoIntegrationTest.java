package br.tec.db.desafio_votacao;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.tec.db.desafio_votacao.domain.entities.Pauta;
import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;
import br.tec.db.desafio_votacao.shared.utils.DateUtils;

@SpringBootTest(classes = br.tec.db.desafio_votacao.DesafioVotacaoApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@DisplayName("Testes de Integração - Sessões Votação")
public class SessaoVotacaoIntegrationTest extends AbstractIntegrationTest {

    private final String RECURSO = "/v1/sessoes-votacao";
    
    private Pauta pauta;
    private SessaoVotacao sessaoVotacao;

    @BeforeEach
    void setup() {
    	this.mapper.findAndRegisterModules();
        this.pauta = super.salvarPautaAleatoria();
        this.sessaoVotacao = super.salvarSessaoVotacao(this.pauta, LocalDateTime.now().plusDays(1));
    }
    
    @Test
    @Order(1)
    @DisplayName("Deve listar sessões de votação com sucesso.")
    public void testDeveListarSessoesVotacaoComSucesso() throws Exception {
    	String urlPath = new StringBuilder().append(RECURSO).append("?idPauta={idPauta}&idSessaoVotacao={idSessaoVotacao}").toString();
    	Long idPauta = this.pauta.getId();
    	Long idSessaoVotacao = this.sessaoVotacao.getId();
    	
        mockMvc
        	.perform(MockMvcRequestBuilders.get(urlPath, idPauta, idSessaoVotacao))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(idSessaoVotacao))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].assunto").value(this.pauta.getAssunto()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].emAberto").value(true));
    }
    
    @Test
    @Order(2)
    @DisplayName("Deve apresentar erro ao listar sessões de votação com filtro idPauta inválido.")
    public void testDeveFalharAoListarSessoesVotacaoInformandoIdPautaInvalido() throws Exception {
    	String urlPath = new StringBuilder().append(RECURSO).append("?idPauta={idPauta}").toString();
    	String idPauta = UUID.randomUUID().toString();
    	
        mockMvc
        	.perform(MockMvcRequestBuilders.get(urlPath, idPauta))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.erros[0].campo").value("idPauta"));
    }
    
    @Test
    @Order(3)
    @DisplayName("Deve obter uma sessão de votação por ID com sucesso.")
    public void testDeveObterUmaPautaPorIDComSucesso() throws Exception {
    	String urlPath = new StringBuilder().append(RECURSO).append("/").append(this.sessaoVotacao.getId()).toString();
    	Long idSessaoVotacao = this.sessaoVotacao.getId();
    	
        mockMvc
            .perform(MockMvcRequestBuilders.get(urlPath))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(idSessaoVotacao))
            .andExpect(MockMvcResultMatchers.jsonPath("$.assunto").value(this.pauta.getAssunto()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.emAberto").value(true));
    }
    
    @Test
    @Order(4)
    @DisplayName("Deve falhar ao tentar buscaruma sessão de votação com um ID inexistente.")
    public void testDeveFalharAoBuscarUmaPautaComIDInexistente() throws Exception {
    	String urlPath = new StringBuilder().append(RECURSO).append("/").append(this.faker.number().digits(11)).toString();
    	
        mockMvc
            .perform(MockMvcRequestBuilders.get(urlPath))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test
    @Order(5)
    @DisplayName("Deve cadastrar uma nova sessão votação com sucesso.")
    public void testDeveCadastrarSessaoVotacaoComSucesso() throws Exception {
    	Pauta novaPauta = super.salvarPautaAleatoria();
    	LocalDateTime dataHoraEncerramento = LocalDateTime.now().plusDays(1);
    	String dataHoraEncerramentoStr = DateUtils.converteLocalDateTimeParaString(dataHoraEncerramento);
    	String sessaoVotacaoJson = String.format("{ \"idPauta\": %d, \"dataHoraEncerramento\": \"%s\" }", novaPauta.getId(), dataHoraEncerramentoStr);
    	
        mockMvc
            .perform(MockMvcRequestBuilders.post(RECURSO)
            .contentType(MediaType.APPLICATION_JSON)
            .content(sessaoVotacaoJson))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.assunto").value(novaPauta.getAssunto()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.emAberto").value(true));
    }
    
    @Test
    @Order(6)
    @DisplayName("Deve cadastrar uma nova sessão votação com sucesso sem informar dataHoraEncerramento.")
    public void testDeveCadastrarSessaoVotacaoSemInformarDataHoraEncerramentoComSucesso() throws Exception {
    	Pauta novaPauta = salvarPautaAleatoria();
    	String sessaoVotacaoJson = String.format("{ \"idPauta\": %d }", novaPauta.getId());
    	
        mockMvc
            .perform(MockMvcRequestBuilders.post(RECURSO)
            .contentType(MediaType.APPLICATION_JSON)
            .content(sessaoVotacaoJson))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.assunto").value(novaPauta.getAssunto()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.emAberto").value(true));
    }
    
    @Test
    @Order(7)
    @DisplayName("Deve falhar ao cadastrar uma nova sessão de votação com os campos do body nulos.")
    public void testDeveFalharAoCadastrarSessaoVotacaoComOsCamposDoBodyNulos() throws Exception {
    	String jsonVazio = "{}";
    	
    	mockMvc
        .perform(MockMvcRequestBuilders.post(RECURSO)
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonVazio))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("idPauta")));
    }
    
    @Test
    @Order(8)
    @DisplayName("Deve falhar ao cadastrar uma nova sessão de votação com ID Pauta que não existe.")
    public void testDeveFalharAoCadastrarSessaoVotacaoComIdPautaQueNaoExiste() throws Exception {
    	super.salvarSessaoVotacao(pauta, LocalDateTime.now().plusDays(1));
    	Long idPauta = Long.valueOf(faker.number().digits(11));
    	LocalDateTime dataHoraEncerramento = LocalDateTime.now().plusDays(1);
    	String dataHoraEncerramentoStr = DateUtils.converteLocalDateTimeParaString(dataHoraEncerramento);
    	String sessaoVotacaoJson = String.format("{ \"idPauta\": %d, \"dataHoraEncerramento\": \"%s\" }", idPauta, dataHoraEncerramentoStr);
    	
        mockMvc
            .perform(MockMvcRequestBuilders.post(RECURSO)
            .contentType(MediaType.APPLICATION_JSON)
            .content(sessaoVotacaoJson))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test
    @Order(9)
    @DisplayName("Deve falhar ao cadastrar uma nova sessão de votação para uma pauta que já possui uma sessão aberta.")
    public void testDeveFalharAoCadastrarSessaoVotacaoParaUmaPautaQueJaPossuiSessaoAberta() throws Exception {
    	LocalDateTime dataHoraEncerramento = LocalDateTime.now().plusDays(1);
    	final LocalDateTime dataHoraEncerramentoSemSegundos = dataHoraEncerramento.truncatedTo(ChronoUnit.MINUTES);
    	
    	Pauta novaPauta = super.salvarPautaAleatoria();
    	super.salvarSessaoVotacao(novaPauta, dataHoraEncerramentoSemSegundos);
    	String dataHoraEncerramentoStr = DateUtils.converteLocalDateTimeParaString(dataHoraEncerramentoSemSegundos);

    	String sessaoVotacaoJson = String.format("{ \"idPauta\": %d, \"dataHoraEncerramento\": \"%s\" }", novaPauta.getId(), dataHoraEncerramentoStr);
    	
        mockMvc
            .perform(MockMvcRequestBuilders.post(RECURSO)
            .contentType(MediaType.APPLICATION_JSON)
            .content(sessaoVotacaoJson))
            .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }
    
}
