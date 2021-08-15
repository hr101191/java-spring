package com.hurui.keycloak;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.NoSuchElementException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
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
		//bypass TLS when working with ldaps
		trustSelfSignedSSL();
	}
	
	public static void trustSelfSignedSSL() {
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			X509TrustManager x509TrustManager = new X509TrustManager() {
				
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				
				@Override
				public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

				}
				
				@Override
				public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

				}
			};
			sslContext.init(null, new TrustManager[] {x509TrustManager}, null);
			SSLContext.setDefault(sslContext);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
