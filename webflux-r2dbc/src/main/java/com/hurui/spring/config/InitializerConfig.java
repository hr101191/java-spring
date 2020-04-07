package com.hurui.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.connectionfactory.init.CompositeDatabasePopulator;
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer;
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator;

import io.r2dbc.spi.ConnectionFactory;

@Configuration
public class InitializerConfig {

	/*
	 * Creates a in-memory H2 database on port 9000
	 * Executes all the .sql script in src/main/resources
	 */
	@Bean
	public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
	    ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
	    initializer.setConnectionFactory(connectionFactory);
	    CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
	    populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
	    populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("data.sql")));
	    initializer.setDatabasePopulator(populator);
	    return initializer;
	}	
}
