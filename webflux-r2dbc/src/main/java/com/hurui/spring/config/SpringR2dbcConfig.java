package com.hurui.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.core.DatabaseClient;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;

@Configuration
public class SpringR2dbcConfig extends AbstractR2dbcConfiguration {
	
	private static final Logger logger = LoggerFactory.getLogger(new Object() { }.getClass().getEnclosingClass());

	@Override
	@Bean
	public ConnectionFactory connectionFactory() {
		logger.info("Initializing connection factory...");
		return ConnectionFactories.get("r2dbc:h2:mem:///test?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
	}
	
	@Bean
	public DatabaseClient databaseClient() {
		DatabaseClient databaseClient = DatabaseClient.builder()
				.connectionFactory(connectionFactory())
				.build();
		return databaseClient;
	}

}
