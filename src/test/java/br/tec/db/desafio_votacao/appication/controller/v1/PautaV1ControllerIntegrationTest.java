package br.tec.db.desafio_votacao.appication.controller.v1;

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

@SpringBootTest(classes = br.tec.db.desafio_votacao.DesafioVotacaoApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@DisplayName("Testes de Integração - Pautas")
public class PautaV1ControllerIntegrationTest extends AbstractIntegrationTest {

    private final String RECURSO = "/v1/pautas";
    
    private Pauta pauta;

    @BeforeEach
    void setup() {
    	this.mapper.findAndRegisterModules();
        this.pauta = super.salvarPautaAleatoria();
    }
    
    @Test
    @Order(1)
    @DisplayName("Deve listar pautas com sucesso.")
    public void testDeveListarPautasComSucesso() throws Exception {
    	String urlPath = new StringBuilder().append(RECURSO).append("?id={id}&assunto={assunto}").toString();
    	Long id = this.pauta.getId();
    	String assunto = this.pauta.getAssunto();
    	
        mockMvc
        	.perform(MockMvcRequestBuilders.get(urlPath, id, assunto))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(this.pauta.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].assunto").value(this.pauta.getAssunto()));
    }
    
    @Test
    @Order(2)
    @DisplayName("Deve apresentar erro ao listar pautas com filtro id inválido.")
    public void testDeveFalharAoListarPautasInformandoIDInvalido() throws Exception {
    	String urlPath = new StringBuilder().append(RECURSO).append("?id={id}").toString();
    	String id = UUID.randomUUID().toString();
    	
        mockMvc
        	.perform(MockMvcRequestBuilders.get(urlPath, id))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.erros[0].campo").value("id"));
    }
    
    @Test
    @Order(3)
    @DisplayName("Deve obter uma pauta por ID com sucesso.")
    public void testDeveObterUmaPautaPorIDComSucesso() throws Exception {
    	String urlPath = new StringBuilder().append(RECURSO).append("/").append(this.pauta.getId()).toString();
    	
        mockMvc
            .perform(MockMvcRequestBuilders.get(urlPath))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(this.pauta.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.assunto").value(this.pauta.getAssunto()));
    }
    
    @Test
    @Order(4)
    @DisplayName("Deve falhar ao tentar buscar uma pauta com um ID inexistente.")
    public void testDeveFalharAoBuscarUmaPautaComIDInexistente() throws Exception {
    	String urlPath = new StringBuilder().append(RECURSO).append("/").append(this.faker.number().digits(11)).toString();
    	
        mockMvc
            .perform(MockMvcRequestBuilders.get(urlPath))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test
    @Order(5)
    @DisplayName("Deve cadastrar uma nova pauta com sucesso.")
    public void testDeveCadastrarPautaComSucesso() throws Exception {
    	Pauta novaPauta = super.criarPautaAleatoria();
    	String associadoJson = mapper.writeValueAsString(novaPauta);
    	
        mockMvc
            .perform(MockMvcRequestBuilders.post(RECURSO)
            .contentType(MediaType.APPLICATION_JSON)
            .content(associadoJson))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.assunto").value(novaPauta.getAssunto()));
    }
    
    @Test
    @Order(6)
    @DisplayName("Deve falhar ao cadastrar uma nova pauta com os campos do body nulos.")
    public void testDeveFalharAoCadastrarPautaComOsCamposDoBodyNulos() throws Exception {
    	String jsonVazio = "{}";
    	
        mockMvc
            .perform(MockMvcRequestBuilders.post(RECURSO)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonVazio))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("assunto")));
    }
    
}
