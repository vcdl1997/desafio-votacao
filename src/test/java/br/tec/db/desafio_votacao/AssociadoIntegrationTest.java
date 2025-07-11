package br.tec.db.desafio_votacao;

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

import br.tec.db.desafio_votacao.domain.entities.Associado;

@SpringBootTest(classes = br.tec.db.desafio_votacao.DesafioVotacaoApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@DisplayName("Testes de Integração - Associados")
public class AssociadoIntegrationTest extends AbstractIntegrationTest {
    
    private final String RECURSO = "/v1/associados";
    
    private Associado associado;

    @BeforeEach
    void setup() {
    	this.mapper.findAndRegisterModules();
        this.associado = super.salvarAssociadoAleatorio();
    }
    
    @Test
    @Order(1)
    @DisplayName("Deve listar associados com sucesso.")
    public void testDeveListarAssociadosComSucesso() throws Exception {
    	String urlPath = new StringBuilder().append(RECURSO).append("?id={id}&cpf={cpf}&nome={nome}&email={email}").toString();
    	Long id = this.associado.getId();
    	Long cpf = this.associado.getCpf();
    	String nome = this.associado.getNome();
    	String email = this.associado.getEmail();
    	
        mockMvc
        	.perform(MockMvcRequestBuilders.get(urlPath, id, cpf, nome, email))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(this.associado.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].cpf").value(this.associado.getCpf()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].nome").value(this.associado.getNome()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].email").value(this.associado.getEmail()));
    }
    
    @Test
    @Order(2)
    @DisplayName("Deve apresentar erro ao listar associados com filtro cpf inválido.")
    public void testDeveFalharAoListarAssociadosInformandoCPFInvalido() throws Exception {
    	String urlPath = new StringBuilder().append(RECURSO).append("?cpf={cpf}").toString();
    	String cpf = this.faker.number().digits(10);
    	
        mockMvc
        	.perform(MockMvcRequestBuilders.get(urlPath, cpf))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.erros[0].campo").value("cpf"));
    }
    
    @Test
    @Order(3)
    @DisplayName("Deve obter um associado por ID com sucesso.")
    public void testDeveObterUmAssociadoPorIDComSucesso() throws Exception {
    	String urlPath = new StringBuilder().append(RECURSO).append("/").append(this.associado.getId()).toString();
    	
        mockMvc
            .perform(MockMvcRequestBuilders.get(urlPath))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(this.associado.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(this.associado.getCpf()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(this.associado.getNome()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(this.associado.getEmail()));
    }
    
    @Test
    @Order(4)
    @DisplayName("Deve falhar ao tentar buscar um associado com um ID inexistente.")
    public void testDeveFalharAoBuscarUmAssociadoComUmIDInexistente() throws Exception {
    	Long idAssociado = Long.valueOf(this.faker.number().digits(11));
    	String urlPath = new StringBuilder().append(RECURSO).append("/").append(idAssociado).toString();
    	
        mockMvc
            .perform(MockMvcRequestBuilders.get(urlPath))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test
    @Order(5)
    @DisplayName("Deve cadastrar um novo associado com sucesso.")
    public void testDeveCadastrarAssociadoComSucesso() throws Exception {
    	Associado novoAssociado = super.criarAssociadoAleatorio();
    	String associadoJson = mapper.writeValueAsString(novoAssociado);
    	
        mockMvc
            .perform(MockMvcRequestBuilders.post(RECURSO)
            .contentType(MediaType.APPLICATION_JSON)
            .content(associadoJson))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(novoAssociado.getCpf()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(novoAssociado.getNome()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(novoAssociado.getEmail()));
    }
    
    @Test
    @Order(6)
    @DisplayName("Deve falhar ao cadastrar um novo associado com os campos do body nulos.")
    public void testDeveFalharAoCadastrarAssociadoComOsCamposDoBodyNulos() throws Exception {
    	String jsonVazio = "{}";
    	
        mockMvc
            .perform(MockMvcRequestBuilders.post(RECURSO)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonVazio))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("cpf")))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("nome")))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("email")));
    }
    
    @Order(7)
    @DisplayName("Deve falhar ao cadastrar um novo associado utilizando um CPF que já está está vinculado a outro associado.")
    public void testDeveFalharAoCadastrarUmNovoAssociadoUtilizandoUmCpfQueJaEstaVinculadoAOutroAssociado() throws Exception {
    	String associadoJson = mapper.writeValueAsString(this.associado);
    	
        mockMvc
            .perform(MockMvcRequestBuilders.post(RECURSO)
            .contentType(MediaType.APPLICATION_JSON)
            .content(associadoJson))
            .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }
    
    @Test
    @Order(8)
    @DisplayName("Deve atualizar um associado com sucesso.")
    public void testDeveAtualizarUmAssociadoComSucesso() throws Exception {
    	String urlPath = new StringBuilder().append(RECURSO).append("/").append(this.associado.getId()).toString();
    	this.associado.setNome(this.faker.name().fullName());
    	this.associado.setEmail(this.faker.internet().emailAddress());
    	String associadoJson = mapper.writeValueAsString(this.associado);
    	
        mockMvc
            .perform(MockMvcRequestBuilders.put(urlPath)
            .contentType(MediaType.APPLICATION_JSON)
            .content(associadoJson))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(this.associado.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(this.associado.getCpf()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(this.associado.getNome()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(this.associado.getEmail()));
    }
    
    @Test
    @Order(9)
    @DisplayName("Deve falhar ao tentar atualizar um associado que não existe.")
    public void testDeveFalharAoTentarAtualizarUmAssociadoQueNaoExiste() throws Exception {
    	String urlPath = new StringBuilder().append(RECURSO).append("/").append(this.faker.number().digits(11)).toString();
    	Associado associado = criarAssociadoAleatorio();
    	String associadoJson = mapper.writeValueAsString(associado);
    	
        mockMvc
            .perform(MockMvcRequestBuilders.put(urlPath)
            .contentType(MediaType.APPLICATION_JSON)
            .content(associadoJson))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test
    @Order(10)
    @DisplayName("Deve falhar ao tentar atualizar um associado com os campos do body nulos.")
    public void testDeveFalharAoTentarAtualizarUmAssociadoComOsCamposDoBodyNulos() throws Exception {
    	String urlPath = new StringBuilder().append(RECURSO).append("/").append(this.associado.getId()).toString();
    	String jsonVazio = "{}";
    	
        mockMvc
            .perform(MockMvcRequestBuilders.put(urlPath)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonVazio))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("nome")))
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("email")));
    }
}
