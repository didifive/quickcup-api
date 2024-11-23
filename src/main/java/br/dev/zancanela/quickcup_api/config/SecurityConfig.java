package br.dev.zancanela.quickcup_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String ADMIN_ROLE = "ADMIN";
    public static final String ATENDENTE_ROLE = "ATENDENTE";
    public static final String ATENDENTE_USERNAME = "atendente";
    public static final String ADMIN_USERNAME = "admin";
    public static final String DEV_USERNAME = "dev";
    public static final String DEV_ROLE = "DEV";
    public static final String ERROR_PATH = "/error";
    private final String usersDefaultPassword;
    private final ApiKeyAuthorizationManager apiKeyAuthorizationManager;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(
            @Value("${quickcup.users.default.password}") String usersDefaultPassword,
            ApiKeyAuthorizationManager apiKeyAuthorizationManager,
            CorsConfigurationSource corsConfigurationSource) {
        this.apiKeyAuthorizationManager = apiKeyAuthorizationManager;
        this.corsConfigurationSource = corsConfigurationSource;
        this.usersDefaultPassword = usersDefaultPassword;
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user1 = User.builder()
                .username(ATENDENTE_USERNAME)
                .password(passwordEncoder.encode(usersDefaultPassword))
                .roles(ATENDENTE_ROLE)
                .build();
        UserDetails user2 = User.builder()
                .username(ADMIN_USERNAME)
                .password(passwordEncoder.encode(usersDefaultPassword))
                .roles(ADMIN_ROLE)
                .build();
        UserDetails user3 = User.builder()
                .username(DEV_USERNAME)
                .password(passwordEncoder.encode(usersDefaultPassword))
                .roles(DEV_ROLE)
                .build();
        return new InMemoryUserDetailsManager(user1, user2, user3);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers(ERROR_PATH).permitAll()
                        //Aqui devem ser incluídas todas as pastas e arquivos de assets publicas do projeto
                        //para carregamento do conteúdo estático corretamente
                        .requestMatchers("/assets/bootstrap/css/bootstrap.min.css").permitAll()
                        .requestMatchers("/assets/bootstrap/js/bootstrap.min.js").permitAll()
                        .requestMatchers("/assets/css/styles.min.css").permitAll()
                        .requestMatchers("/assets/img/quickcup-logo.png").permitAll()
                        .requestMatchers("/assets/js/jquery-3.7.1.min.js").permitAll()
                        .requestMatchers("/assets/js/modalsandpopover.js").permitAll()
                        //Proteção dos endpoints da administração da aplicação
                        .requestMatchers("/cliente**/**").hasRole(ADMIN_ROLE)
                        .requestMatchers("/funcionamento**/**").hasRole(ADMIN_ROLE)
                        .requestMatchers("/grupo**/**").hasRole(ADMIN_ROLE)
                        .requestMatchers("/produto**/**").hasRole(ADMIN_ROLE)
                        //Os endpoints API v1 exigem API KEY verificada através do apiKeyAuthorizationManager
                        .requestMatchers("/api/v1/**").access(apiKeyAuthorizationManager)
                        //Permissão para acesso ao openapi/swagger apidocs v3
                        //Ideal colocar controle para quando a aplicação for usada em propósito real
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").hasRole(DEV_ROLE)
                        //Por padrão será exigido autenticação
                        .anyRequest().authenticated()

                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                        .loginProcessingUrl("/login"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(logoutSuccessHandler())
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/v1/**"))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> response.sendRedirect(ERROR_PATH))
                        .accessDeniedHandler((request, response, accessDeniedException) -> response.sendRedirect(ERROR_PATH))
                );
        return httpSecurity.build();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> response.sendRedirect("/");
    }
}
