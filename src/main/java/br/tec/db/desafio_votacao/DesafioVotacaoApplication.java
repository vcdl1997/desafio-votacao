package br.tec.db.desafio_votacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "br.tec.db.desafio_votacao.domain.entities")
@EnableJpaRepositories(basePackages = "br.tec.db.desafio_votacao.infrastructure.repositories")
public class DesafioVotacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioVotacaoApplication.class, args);
	}

}
