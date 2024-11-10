package br.dev.zancanela.quickcup_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static br.dev.zancanela.quickcup_api.util.ApiConstants.*;

@Configuration
public class GlobalCorsConfig {

    @Value("${application.cross-origin-url}")
    private String crossOriginUrl;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping(API_REST_ROOT_URN)
                        .allowedOrigins(crossOriginUrl)
                        .allowedMethods(
                                HttpMethod.GET.name(),
                                HttpMethod.POST.name(),
                                HttpMethod.PUT.name(),
                                HttpMethod.DELETE.name(),
                                HttpMethod.OPTIONS.name())
                        .allowedHeaders(
                                HEADER_CONTENT_TYPE,
                                HEADER_AUTHORIZATION)
                        .allowCredentials(true)
                        .maxAge(UMA_HORA_EM_SEGUNDOS);
            }
        };

    }
}
