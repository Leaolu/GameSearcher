package com.EACH.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;


@Configuration
public class OpenAPIConfigs {
	
	@Bean
	OpenAPI customAPIConfigs(){
		return new OpenAPI().info(new Info().title("Game Researcher")
				.summary("Research Game prices on multiple platforms on a single click")
				.version("V1")
				.description("API to research on Nintendo Store, Xbox Store, Playstation Store, Steam and Epic Games")
				.termsOfService("https://github.com/Leaolu/GameSearcher.git")
				.license(new License()
						.name("Apache 2.0")
						.url("https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-ui/1.8.0")));
		
	}

}
