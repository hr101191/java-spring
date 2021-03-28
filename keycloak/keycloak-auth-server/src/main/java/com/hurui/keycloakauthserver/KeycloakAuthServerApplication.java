package com.hurui.keycloakauthserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)
@ComponentScan("com.hurui")
public class KeycloakAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeycloakAuthServerApplication.class, args);
	}

}
