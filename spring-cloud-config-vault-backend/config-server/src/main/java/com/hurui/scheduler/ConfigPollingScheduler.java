package com.hurui.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hurui.entity.Config;
import com.hurui.entity.ConfigId;
import com.hurui.model.VaultResponse;
import com.hurui.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class ConfigPollingScheduler implements ApplicationListener<ApplicationStartedEvent> {

    private final RestTemplate restTemplate;
    private final String vaultUrl;
    private final ConfigRepository configRepository;

    public ConfigPollingScheduler(@Value("${application.vault.url}") String vaultUrl, RestTemplate restTemplate, ConfigRepository configRepository) {
        this.vaultUrl = vaultUrl;
        this.restTemplate = restTemplate;
        this.configRepository = configRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        ObjectMapper objectMapper = new ObjectMapper();
        //TODO: 1. parse jwt from Azure AD openid response
        //TODO: 2. exchange for X-Vault-Token using the jwt
        MultiValueMap<String, String> httpHeaders = new LinkedMultiValueMap<>();
        httpHeaders.add("X-Vault-Token", "s.eyf6DDg8Rnu7erqLaOEuF5T2"); //Using root key in this demo
        ResponseEntity<VaultResponse> responseEntity = this.restTemplate.exchange(this.vaultUrl, HttpMethod.GET, new HttpEntity<>(httpHeaders), VaultResponse.class);
        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            try {
                VaultResponse vaultResponse = responseEntity.getBody();
                System.out.println(objectMapper.writeValueAsString(vaultResponse));
                String application = "secret"; //maps to kv2 store label in vault
                String profile = "test"; //spring profile which should be created in the kv vault
                String label = "master"; //required param by cloud config server but is not used by jdbc implementation
                Map<String, String> kvp = vaultResponse.getData().getData();
                kvp.forEach((k, v) -> {
                    Config config = new Config(application, profile, label, k, v);
                    this.configRepository.save(config);
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
