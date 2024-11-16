package br.dev.zancanela.quickcup_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

import static br.dev.zancanela.quickcup_api.util.ApiConstants.*;

@Configuration
public class GlobalCorsConfig {

    @Value("${application.cross-origin-urls}")
    private String crossOriginUrls;

    private List<String> allowedMethods =
            List.of(HttpMethod.GET.name(),
                    HttpMethod.POST.name(),
                    HttpMethod.PUT.name(),
                    HttpMethod.DELETE.name(),
                    HttpMethod.OPTIONS.name());

    private List<String> allowedHeaders = List.of(HEADER_CONTENT_TYPE,HEADER_AUTHORIZATION);

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping(API_REST_ROOT_URN)
                        .allowedOrigins(crossOriginUrls)
                        .allowedMethods(String.valueOf(allowedMethods))
                        .allowedHeaders(String.valueOf(allowedHeaders))
                        .allowCredentials(true)
                        .maxAge(UMA_HORA_EM_SEGUNDOS);
            }
        };

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.stream(crossOriginUrls.split(",")).toList());
        configuration.setAllowedMethods(allowedMethods);
        configuration.setAllowedHeaders(allowedHeaders);
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
