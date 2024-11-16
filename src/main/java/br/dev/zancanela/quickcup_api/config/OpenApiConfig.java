package br.dev.zancanela.quickcup_api.config;

import br.dev.zancanela.quickcup_api.util.ApiConstants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Localhost")})
public class OpenApiConfig {

    @Bean
    @SecurityRequirement(name = "QUICKCUP_API_KEY")
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(ApiConstants.API_TITLE)
                        .version(ApiConstants.API_VERSION)
                        .description(ApiConstants.API_DESCRIPTION)
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().
                                name(ApiConstants.API_LICENSE).
                                url(ApiConstants.API_LICENSE_URL))
                        .contact(new Contact()
                                .name(ApiConstants.CONTACT_NAME)
                                .url(ApiConstants.CONTACT_URL)))
                .components(new Components()
                        .addSecuritySchemes(ApiConstants.QUICKCUP_API_KEY_SECURITY_SCHEMES,
                                new SecurityScheme()
                                        .name(AUTHORIZATION)
                                        .in(SecurityScheme.In.HEADER)
                                        .type(SecurityScheme.Type.APIKEY)));
    }
}
