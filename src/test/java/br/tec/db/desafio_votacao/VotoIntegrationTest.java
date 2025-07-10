package br.tec.db.desafio_votacao;

import java.time.LocalDateTime;

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

import br.tec.db.desafio_votacao.application.dto.voto.request.VotoRequestDTO;
import br.tec.db.desafio_votacao.domain.entities.Associado;
import br.tec.db.desafio_votacao.domain.entities.Pauta;
import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;
import br.tec.db.desafio_votacao.domain.enums.RespostaVotoEnum;

@SpringBootTest(classes = br.tec.db.desafio_votacao.DesafioVotacaoApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@DisplayName("Testes de Integração - Votos")
public class VotoIntegrationTest extends AbstractIntegrationTest {

    private final String RECURSO = "/v1/votos";
    
    private Associado associado;
    private Pauta pauta;
    private SessaoVotacao sessaoVotacao;

    @BeforeEach
    void setup() {
    	this.mapper.findAndRegisterModules();
    	this.associado = super.salvarAssociadoAleatorio();
        this.pauta = super.salvarPautaAleatoria();
        this.sessaoVotacao = super.salvarSessaoVotacao(this.pauta, LocalDateTime.now().plusDays(1));
    }
    
    @Test
    @Order(1)
    @DisplayName("Deve cadastrar um voto com sucesso.")
    public void testDeveCadastrarVotoComSucesso() throws Exception {
    	VotoRequestDTO dto = VotoRequestDTO
    		.builder()
    		.idAssociado(this.associado.getId().toString())
    		.idSessaoVotacao(this.sessaoVotacao.getId().toString())
    		.respostaVoto("SIM")
    		.build()
    	;
    		
    	String votoJson = mapper.writeValueAsString(dto);
    	
        mockMvc
            .perform(MockMvcRequestBuilders.post(RECURSO)
            .contentType(MediaType.APPLICATION_JSON)
            .content(votoJson))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("mensagem")))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("dataHoraRegistro")));
    }
    
    @Test
    @Order(2)
    @DisplayName("Deve falhar ao tentar cadastrar um voto com os campos do body nulos.")
    public void testDeveFalharAoTentarCadastrarVotoComOsCamposDoBodyNulos() throws Exception {
    	String jsonVazio = "{}";
    	
        mockMvc
            .perform(MockMvcRequestBuilders.post(RECURSO)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonVazio))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("idSessaoVotacao")))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("idAssociado")))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("respostaVoto")));
    }
    
    @Test
    @Order(3)
    @DisplayName("Deve falhar ao tentar cadastrar mais de um voto de um associado em uma mesma sessão de votação.")
    public void testDeveFalharAoTentarCadastrarMaisDeUmVotoDeUmAssociadoEmUmaMesmaSessao() throws Exception {
    	super.salvarVoto(this.sessaoVotacao, this.associado, RespostaVotoEnum.SIM);
    	VotoRequestDTO dto = VotoRequestDTO
    		.builder()
    		.idAssociado(this.associado.getId().toString())
    		.idSessaoVotacao(this.sessaoVotacao.getId().toString())
    		.respostaVoto("SIM")
    		.build()
    	;
    		
    	String votoJson = mapper.writeValueAsString(dto);
    	
    	mockMvc
	        .perform(MockMvcRequestBuilders.post(RECURSO)
	        .contentType(MediaType.APPLICATION_JSON)
	        .content(votoJson))
	        .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }
    
    @Test
    @Order(4)
    @DisplayName("Deve falhar ao tentar cadastrar um voto de um associado que não existe.")
    public void testDeveFalharAoTentarCadastrarUmVotoDeUmAssociadoQueNaoExiste() throws Exception {
    	VotoRequestDTO dto = VotoRequestDTO
    		.builder()
    		.idAssociado(this.faker.number().digits(11))
    		.idSessaoVotacao(this.sessaoVotacao.getId().toString())
    		.respostaVoto("SIM")
    		.build()
    	;
    		
    	String votoJson = mapper.writeValueAsString(dto);
    	
    	mockMvc
	        .perform(MockMvcRequestBuilders.post(RECURSO)
	        .contentType(MediaType.APPLICATION_JSON)
	        .content(votoJson))
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test
    @Order(5)
    @DisplayName("Deve falhar ao tentar cadastrar um voto de uma sessão de votação que não existe.")
    public void testDeveFalharAoTentarCadastrarUmVotoDeUmaSessaoVotacaoQueNaoExiste() throws Exception {
    	VotoRequestDTO dto = VotoRequestDTO
    		.builder()
    		.idAssociado(this.associado.getId().toString())
    		.idSessaoVotacao(this.faker.number().digits(11))
    		.respostaVoto("SIM")
    		.build()
    	;
    		
    	String votoJson = mapper.writeValueAsString(dto);
    	
    	mockMvc
	        .perform(MockMvcRequestBuilders.post(RECURSO)
	        .contentType(MediaType.APPLICATION_JSON)
	        .content(votoJson))
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test
    @Order(6)
    @DisplayName("Deve falhar ao tentar cadastrar um voto de uma sessão que está fechada.")
    public void testDeveFalharAoTentarCadastrarUmVotoEmUmaSessaoFechada() throws Exception {
    	this.sessaoVotacao = super.salvarSessaoVotacao(this.pauta, LocalDateTime.now().minusDays(1));
    	
    	VotoRequestDTO dto = VotoRequestDTO
    		.builder()
    		.idAssociado(this.associado.getId().toString())
    		.idSessaoVotacao(this.sessaoVotacao.getId().toString())
    		.respostaVoto("SIM")
    		.build()
    	;
    		
    	String votoJson = mapper.writeValueAsString(dto);
    	
    	mockMvc
	        .perform(MockMvcRequestBuilders.post(RECURSO)
	        .contentType(MediaType.APPLICATION_JSON)
	        .content(votoJson))
	        .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }
    
    @Test
    @Order(7)
    @DisplayName("Deve exibir resultado votação com sucesso.")
    public void testDeveExibirResultadoVotacaoComSucesso() throws Exception {
    	Associado novoAssociado1 = super.salvarAssociadoAleatorio();
    	Associado novoAssociado2 = super.salvarAssociadoAleatorio();
    	
    	super.salvarVoto(this.sessaoVotacao, this.associado, RespostaVotoEnum.SIM);
    	super.salvarVoto(this.sessaoVotacao, novoAssociado1, RespostaVotoEnum.SIM);
    	super.salvarVoto(this.sessaoVotacao, novoAssociado2, RespostaVotoEnum.NAO);
    	
    	this.sessaoVotacao = super.salvarSessaoVotacao(this.pauta, LocalDateTime.now().minusDays(1));
    	
    	String urlPath = new StringBuilder().append(RECURSO).append("?idSessaoVotacao={idSessaoVotacao}").toString();
    	
        mockMvc
        	.perform(MockMvcRequestBuilders.get(urlPath, this.sessaoVotacao.getId()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("pauta")))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("resultado")));
    }
    
    @Test
    @Order(8)
    @DisplayName("Deve falhar ao tentar exibir resultado de uma sessão de votação sem informar o ID da sessão.")
    public void testDeveFalharAoTentarExibirResultadoSemInformarSessaoVotacao() throws Exception {
    	String jsonVazio = "{}";
    	
        mockMvc
            .perform(MockMvcRequestBuilders.post(RECURSO)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonVazio))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("idSessaoVotacao")));
    }
    
    @Test
    @Order(9)
    @DisplayName("Deve falhar ao tentar exibir resultado de uma sessão de votação em andamento.")
    public void testDeveFalharAoTentarExibirResultadoSessaoVotacaoEmAndamento() throws Exception {
    	Associado novoAssociado1 = super.salvarAssociadoAleatorio();
    	Associado novoAssociado2 = super.salvarAssociadoAleatorio();
    	
    	super.salvarVoto(this.sessaoVotacao, this.associado, RespostaVotoEnum.SIM);
    	super.salvarVoto(this.sessaoVotacao, novoAssociado1, RespostaVotoEnum.SIM);
    	super.salvarVoto(this.sessaoVotacao, novoAssociado2, RespostaVotoEnum.NAO);
    	
    	String urlPath = new StringBuilder().append(RECURSO).append("?idSessaoVotacao={idSessaoVotacao}").toString();
    	
        mockMvc
        	.perform(MockMvcRequestBuilders.get(urlPath, this.sessaoVotacao.getId()))
        	.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }
    
    @Test
    @Order(10)
    @DisplayName("Deve falhar ao tentar exibir resultado de uma sessão de votação que não existe.")
    public void testDeveFalharAoTentarExibirResultadoSessaoVotacaoQueNaoExiste() throws Exception {
    	Associado novoAssociado1 = super.salvarAssociadoAleatorio();
    	Associado novoAssociado2 = super.salvarAssociadoAleatorio();
    	
    	super.salvarVoto(this.sessaoVotacao, this.associado, RespostaVotoEnum.SIM);
    	super.salvarVoto(this.sessaoVotacao, novoAssociado1, RespostaVotoEnum.SIM);
    	super.salvarVoto(this.sessaoVotacao, novoAssociado2, RespostaVotoEnum.NAO);
    	
    	String urlPath = new StringBuilder().append(RECURSO).append("?idSessaoVotacao={idSessaoVotacao}").toString();
    	
        mockMvc
        	.perform(MockMvcRequestBuilders.get(urlPath, this.faker.number().digits(11)))
        	.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
}
