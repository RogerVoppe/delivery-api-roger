package com.deliverytech.delivery.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Define esta classe como uma fonte de configuração
public class OpenApiConfig {

    @Bean // Diz ao Spring para usar este objeto como a configuração principal do OpenAPI
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("DeliveryTech API") // Título da sua API
                .version("1.0.0") // Versão
                .description("API RESTful completa para um sistema de delivery, desenvolvida no curso de extensão.")
                .contact(new Contact()
                    .name("Roger Voppe") // Seu nome
                    .email("roger@exemplo.com") // Seu e-mail
                    .url("https://github.com/RogerVoppe/delivery-api-roger")) // Seu GitHub
                .license(new License()
                    .name("Licença MIT")
                    .url("http://springdoc.org"))
            );
    }
}