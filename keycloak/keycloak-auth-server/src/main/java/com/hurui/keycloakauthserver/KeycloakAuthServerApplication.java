package com.hurui.keycloakauthserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import com.hurui.configuration.KeycloakDataSourceConfig;

@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)
@ComponentScan("com.hurui")
@EnableConfigurationProperties({ KeycloakDataSourceConfig.class })
public class KeycloakAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeycloakAuthServerApplication.class, args);
	}

}
