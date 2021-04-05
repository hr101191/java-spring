package com.hurui.keycloak;

import java.io.IOException;
import java.util.Optional;

import org.keycloak.Config;
import org.keycloak.services.util.JsonConfigProviderFactory;
import org.keycloak.util.JsonSerialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

public class EmbeddedJsonConfigProviderFactory extends JsonConfigProviderFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonConfigProviderFactory.class);
	
	@Override
	public Optional<Config.ConfigProvider> create() {
	
		JsonNode node = null;
		try {
			//We try to retrieve the config via System properties set via the ApplicationContextInitializer class
			Optional<String> configOptional = Optional.ofNullable(System.getProperty("application.keycloak.config"));
			if(configOptional.isPresent()) {
				node = JsonSerialization.mapper.readTree(configOptional.get());
			} else {
				logger.warn("Failed to get keycloak json config from system Properties. Exiting the program with status -1.");
				System.exit(-1);
			}
		} catch (IOException ex) {
			logger.error("Failed to parse keycloak json config, stacktrace:", ex);
		}
		return createJsonProvider(node);
	}
	
}
