package com.hurui.service;

import com.hurui.entity.Config;
import com.hurui.model.VaultResponse;
import com.hurui.repository.ConfigRepository;
import com.hurui.util.VaultConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class ConfigServiceImpl implements ConfigService {

    private static final Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);

    private final String vaultToken;
    private final String vaultUrlTemplate;
    private final Map<String, List<String>> secretStoreMap;
    private final RestTemplate restTemplate;
    private final ConfigRepository configRepository;

    public ConfigServiceImpl(
            @Value("${application.vault.token}") String vaultToken,
            @Value("${application.vault.url-template}") String vaultUrlTemplate,
            @Value("#{${application.vault.secret-store-map}}") Map<String, List<String>> secretStoreMap,
            RestTemplate restTemplate,
            ConfigRepository configRepository
    ) {
        this.vaultToken = vaultToken;
        this.vaultUrlTemplate = vaultUrlTemplate;
        this.secretStoreMap = secretStoreMap;
        this.restTemplate = restTemplate;
        this.configRepository = configRepository;
    }

    @Override
    public void syncDatabaseConfigWithVault() {
        List<Config> configs = new ArrayList<>();
        String label = "master";
        //Optionally a jwt token can be accepted if vault is secured by an openid provider. Use the restTemplate to fetch the jwt token from an openid provider.
        MultiValueMap<String, String> httpHeaders = new LinkedMultiValueMap<>();
        httpHeaders.add(VaultConstants.VAULT_AUTHENTICATION_HEADER, this.vaultToken);
        this.secretStoreMap.forEach((k, v) -> {
            v.forEach(secretName -> {
                try {
                    String url = this.vaultUrlTemplate.replace(VaultConstants.SECRET_STORE_NAME_PLACEHOLDER, k).replace(VaultConstants.SECRET_NAME_PLACEHOLDER, secretName);
                    logger.info("Polling application configurations from vault. Url: {}", url);
                    ResponseEntity<VaultResponse> responseEntity = this.restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(httpHeaders), VaultResponse.class);
                    if(responseEntity.getStatusCode().is2xxSuccessful()) {
                        VaultResponse vaultResponse = responseEntity.getBody();
                        Map<String, String> configMap = vaultResponse.getData().getData();
                        configMap.forEach((springConfigKey, springConfigValue) -> {
                            Config config = new Config(k, secretName, label, springConfigKey, springConfigValue);
                            configs.add(config);
                        });
                    } else {
                        logger.warn("Expecting 2xx success http status code from Vault API. Actual http status code: {}", responseEntity.getStatusCode().value());
                    }
                } catch (HttpStatusCodeException ex) {
                    logger.error(
                            "Expecting 2xx success http status code from Vault API. Actual http status code: {} | Response Body: {} | Stacktrace: ",
                            ex.getStatusCode().value(),
                            ex.getResponseBodyAsString(),
                            ex
                    );
                } catch (Exception ex) {
                    logger.error("Failed to connect to Vault server. Stacktrace: ", ex);
                }
                if(configs.size() > 0) {
                    configRepository.saveAll(configs);
                    logger.info("Application: {} | Profile: {} | Label: {} | Successfully synchronized all configuration with remote Vault server.", k, secretName, label);
                } else {
                    logger.warn("Remote secret store is empty. Store Name: {} | Secret Name: {}", k, secretName);
                }
            });
        });
    }
}
