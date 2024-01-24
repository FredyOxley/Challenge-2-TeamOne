package com.compassuol.sp.challenge.ecommerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("REST API - E-commerce")
                                .description("2º Springboot challenge.<br><br> " +
                                        "API REST para um e-commerce, utilizando as tecnologias e conhecimentos aprendidos no curso.<br><br>" +
                                        "<a href='mailto:alex.spohr.pb@compasso.com.br'>Contact Alex Spohr</a><br><br>" +
                                        "<a href='mailto:fredy.oxley.pb@compasso.com.br'>Contact Fredy Tulio Pieren Oxley</a><br><br>" +
                                        "<a href='mailto:leandro.paupitz.pb@compasso.com.br'>Contact Leandro Paupitz</a><br><br>" +
                                        "<a href='mailto:pedro.bastos.pb@compasso.com.br'>Contact Pedro Antonio Carvalho Bastos</a><br><br>" +
                                        "<a href='mailto:rafael.cazali.pb@compasso.com.br'>Contact Rafael Henrique Megier Cazali</a><br><br>" +
                                        "<a href='mailto:raphael.araujo.pb@compasso.com.br'>Contact Raphael da Silva Araújo</a>"
                                )
                                .version("v1")
                                .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
                );
    }
}