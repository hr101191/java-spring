package com.hurui.initializer;

import java.net.URL;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.keycloak.util.JsonSerialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.PropertySourcesPlaceholdersResolver;
import org.springframework.boot.context.properties.bind.validation.ValidationBindHandler;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hurui.configuration.KeycloakDataSourceConfig;

public class KeycloakConfigInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	private static final Logger logger = LoggerFactory.getLogger(KeycloakConfigInitializer.class);
	
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		//Manually create the keycloak datasource config before spring beans initialize
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(environment);
		PropertySourcesPlaceholdersResolver resolver = new PropertySourcesPlaceholdersResolver(environment);
		Binder binder = new Binder(sources, resolver);
		Bindable<KeycloakDataSourceConfig> bindable = Bindable.of(KeycloakDataSourceConfig.class);
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		SpringValidatorAdapter springValidator = new SpringValidatorAdapter(validator);
		BindResult<KeycloakDataSourceConfig> bindResult = binder.bind("application.keycloak.server.datasource", bindable, new ValidationBindHandler(springValidator));
		//Set the config as JSON in the System properties so that jax-rs resources can retrieve it when it is deployed by the ResteasyEmbeddedServletInitializer
		//Note: ResteasyEmbeddedServletInitializer does not wire spring beans, however runtime autowiring will work because of the resteasy spring plugin.
		//We have to use this workaround to supply the value to the jax-rs initializing classes
		try {
			URL resource = Thread.currentThread().getContextClassLoader().getResource("META-INF/keycloak-server.json");
			JsonNode config = JsonSerialization.mapper.readTree(resource);
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode keycloakJpaObjectNode = objectMapper.createObjectNode();
			keycloakJpaObjectNode.putPOJO("default", bindResult.get());
			ObjectNode objectNode = (ObjectNode) objectMapper.readTree(config.toString());
			objectNode.replace("connectionsJpa", keycloakJpaObjectNode);
			config = JsonSerialization.mapper.readTree(objectNode.toString());
			System.setProperty("application.keycloak.config", config.toString());
		} catch(Exception ex) {
		logger.warn("Something failed while trying to load keycloak spring configurations, exiting the program with status -1");
			logger.error("Failed to load Keycloak spring configurations before application context initialize. Stacktrace: ", ex);
			System.exit(-1);
		}
	}

}
