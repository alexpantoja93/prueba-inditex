package com.alexpantoja.prueba_inditex.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info =
        @Info(
            title = "Inditex Price API",
            version = "1.0",
            description = "API que devuelve el precio aplicable según reglas comerciales",
            contact = @Contact(name = "Alex Pantoja", email = "alexfidepantoja93@gmail.com"),
            license = @License(name = "Apache 2.0", url = "http://springdoc.org")))
@Configuration
public class OpenApiConfig {}
