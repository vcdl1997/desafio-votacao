package br.tec.db.desafio_votacao.appication.controller.v1;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.tec.db.desafio_votacao.domain.entities.Associado;
import br.tec.db.desafio_votacao.domain.entities.Pauta;
import br.tec.db.desafio_votacao.domain.entities.SessaoVotacao;
import br.tec.db.desafio_votacao.domain.entities.Voto;
import br.tec.db.desafio_votacao.domain.entities.VotoId;
import br.tec.db.desafio_votacao.domain.enums.RespostaVotoEnum;
import br.tec.db.desafio_votacao.domain.repositories.AssociadoRepository;
import br.tec.db.desafio_votacao.domain.repositories.PautaRepository;
import br.tec.db.desafio_votacao.domain.repositories.SessaoVotacaoRepository;
import br.tec.db.desafio_votacao.domain.repositories.VotoRepository;

public abstract class AbstractIntegrationTest {
	
    @Autowired
    protected MockMvc mockMvc;
	
	@Autowired
	protected AssociadoRepository associadoRepository;
	
	@Autowired
	protected PautaRepository pautaRepository;
	
	@Autowired
	protected SessaoVotacaoRepository sessaoVotacaoRepository;
	
	@Autowired
	protected VotoRepository votoRepository;
	
	protected final Faker faker = new Faker();
	
	protected final ObjectMapper mapper = new ObjectMapper();
	
	public Associado criarAssociadoAleatorio() {
		return Associado
			.builder()
			.cpf(Long.valueOf(faker.number().numberBetween(10000000000L, 99999999999L)))
			.nome(faker.name().fullName())
			.email(faker.internet().emailAddress())
			.dataHoraInclusao(LocalDateTime.now())
			.build()
		;
	}
	
	public Associado salvarAssociadoAleatorio() {
		Associado associado = associadoRepository.salvar(criarAssociadoAleatorio());
		Assertions.assertNotNull(associado.getId(), "Associado deveria ter sido persistido com sucesso");
		return associado;
	}
	
	public Pauta criarPautaAleatoria() {
		return Pauta
			.builder()
			.assunto(faker.animal().name())
			.dataHoraInclusao(LocalDateTime.now())
			.build()
		;
	}
	
	public Pauta salvarPautaAleatoria() {
		Pauta pauta = pautaRepository.salvar(criarPautaAleatoria());
		Assertions.assertNotNull(pauta.getId(), "Pauta deveria ter sido persistida com sucesso");
		return pauta;
	}
	
	public SessaoVotacao criarSessaoVotacao(Pauta pauta, LocalDateTime dataHoraEncerramento) {
		return SessaoVotacao
			.builder()
			.pauta(pauta)
			.dataHoraEncerramento(dataHoraEncerramento)
			.dataHoraInclusao(LocalDateTime.now().plusDays(1))
			.build()
		;
	}
	
	public SessaoVotacao salvarSessaoVotacao(Pauta pauta, LocalDateTime dataHoraEncerramento) {
		LocalDateTime dataHoraEncerramentoSemSegundos = null;
		
		if(Objects.nonNull(dataHoraEncerramento)) {
	        dataHoraEncerramentoSemSegundos = dataHoraEncerramento.truncatedTo(ChronoUnit.MINUTES);
		}
		
		SessaoVotacao sessaoVotacao = sessaoVotacaoRepository.salvar(criarSessaoVotacao(pauta, dataHoraEncerramentoSemSegundos));
		Assertions.assertNotNull(pauta.getId(), "Sessão Votacao deveria ter sido persistida com sucesso");
		return sessaoVotacao;
	}
	
	public Voto salvarVoto(SessaoVotacao sessao, Associado associado, RespostaVotoEnum resposta) {
		VotoId id = VotoId.builder().sessao(sessao).associado(associado).build();
				
		Voto voto = Voto
			.builder()
			.id(id)
			.resposta(resposta)
			.dataHoraInclusao(LocalDateTime.now())
			.build()
		;
			
		voto = votoRepository.salvar(voto);
		Assertions.assertNotNull(voto.getId(), "Voto deveria ter sido persistido com sucesso");
		
		return voto;
	}
	
}
