package br.tec.db.desafio_votacao.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "br.tec.db.desafio_votacao.domain.entities")
@ComponentScan(basePackages = {
    "br.tec.db.desafio_votacao.domain",
    "br.tec.db.desafio_votacao.infrastructure",
    "br.tec.db.desafio_votacao.application"
})
@EnableJpaRepositories(basePackages = "br.tec.db.desafio_votacao.infrastructure.repositories")
@EnableCaching
public class DesafioVotacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioVotacaoApplication.class, args);
	}

}
