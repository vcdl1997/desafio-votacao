package br.tec.db.desafio_votacao.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API de Votação",
        version = "1.0",
        description = "Esta API tem como objetivo fornecer os recursos necessários para a gestão e realização de votações em ambientes cooperativistas."
    )
)
public class SwaggerConfig {

}
