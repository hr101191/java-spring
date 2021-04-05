package com.hurui.keycloak;

import java.util.NoSuchElementException;

import javax.ws.rs.ApplicationPath;
import org.keycloak.Config;
import org.keycloak.services.resources.KeycloakApplication;
import org.keycloak.services.util.JsonConfigProviderFactory;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/auth")
public class EmbeddedKeycloakApplication extends KeycloakApplication {
	
	protected void loadConfig() {
		JsonConfigProviderFactory factory = new EmbeddedJsonConfigProviderFactory();
		Config.init(factory.create()
			.orElseThrow(() -> new NoSuchElementException("No value present")));
	}
	
	public EmbeddedKeycloakApplication() {
		super();
	}

}
