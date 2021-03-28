package com.hurui.keycloak;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import org.keycloak.Config;
import org.keycloak.services.ServicesLogger;
import org.keycloak.services.util.JsonConfigProviderFactory;
import org.keycloak.util.JsonSerialization;

import com.fasterxml.jackson.databind.JsonNode;

public class EmbeddedJsonConfigProviderFactory extends JsonConfigProviderFactory {
	
	@Override
	public Optional<Config.ConfigProvider> create() {
	
		JsonNode node = null;
	
		try {
			String configDir = System.getProperty("jboss.server.config.dir");
			if (configDir != null) {
				File f = new File(configDir + File.separator + "keycloak-server.json");
				if (f.isFile()) {
					ServicesLogger.LOGGER.loadingFrom(f.getAbsolutePath());
					node = JsonSerialization.mapper.readTree(f);
				}
			}
	
			if (node == null) {
				URL resource = Thread.currentThread().getContextClassLoader().getResource("META-INF/keycloak-server.json");
				if (resource != null) {
					ServicesLogger.LOGGER.loadingFrom(resource);
					node = JsonSerialization.mapper.readTree(resource);
					//TODO: read value from application properties to manipulate the keycloak-server.json
					//in order to set values such as jdbc properties
				}
			}
		} catch (IOException e) {
			//TODO: use slf4j logger instead of jboss logger
			//LOG.warn("Failed to load JSON config", e);
		}
		return createJsonProvider(node);
	}
	
}
