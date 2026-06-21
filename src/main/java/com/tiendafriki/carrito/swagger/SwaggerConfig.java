package com.tiendafriki.carrito.swagger;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class SwaggerConfig {
 
    @Bean
    public OpenAPI carritoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Carrito de Compras")
                        .description("API REST para la gestión del carrito de compras")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Coreplay")
                                .email("soporte@coreplay.com")));

    }
}



