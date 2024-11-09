package br.dev.zancanela.quickcup_api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Localhost")})
public class QuickCupApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickCupApiApplication.class, args);
	}

}
