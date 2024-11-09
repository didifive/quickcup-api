package br.dev.zancanela.quickcup_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String API_TITLE = "QuickCup API";
    private static final String API_DESCRIPTION = "Api para sistema de entregas";
    private static final String API_VERSION = "0.0.1-SNAPSHOT";
    private static final String API_LICENSE = "MIT License";
    private static final String API_LICENSE_URL = "https://mit-license.org/";
    private static final String CONTACT_NAME = "Luis Zancanela";
    private static final String CONTACT_URL = "https://zancanela.dev.br";

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(API_TITLE)
                        .version(API_VERSION)
                        .description(API_DESCRIPTION)
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().
                                name(API_LICENSE).
                                url(API_LICENSE_URL))
                        .contact(new Contact()
                                .name(CONTACT_NAME)
                                .url(CONTACT_URL)));
    }
}
