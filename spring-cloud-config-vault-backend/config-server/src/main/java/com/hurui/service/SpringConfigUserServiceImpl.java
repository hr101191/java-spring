package com.hurui.service;

import com.hurui.entity.SpringConfigUser;
import com.hurui.entity.SpringConfigUserId;
import com.hurui.model.Data;
import com.hurui.model.SpringConfigUserPrincipal;
import com.hurui.model.VaultResponse;
import com.hurui.repository.SpringConfigUserRepository;
import com.hurui.util.VaultConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class SpringConfigUserServiceImpl implements SpringConfigUserService {

    private static final Logger logger = LoggerFactory.getLogger(SpringConfigUserServiceImpl.class);

    private final String vaultToken;
    private final String vaultUrlTemplate;
    private final String springConfigUsersVaultSecretStore;
    private final String springConfigUsersVaultSecretName;
    private final RestTemplate restTemplate;
    private final SpringConfigUserRepository springConfigUserRepository;
    private final PasswordEncoder passwordEncoder;

    public SpringConfigUserServiceImpl(
            @Value("${application.vault.token}") String vaultToken,
            @Value("${application.vault.url-template}") String vaultUrlTemplate,
            @Value("${application.vault.spring-config-users-secret-store}") String springConfigUsersVaultSecretStore,
            @Value("${application.vault.spring-config-users-secret-name}") String springConfigUsersVaultSecretName,
            RestTemplate restTemplate,
            SpringConfigUserRepository springConfigUserRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.vaultToken = vaultToken;
        this.vaultUrlTemplate = vaultUrlTemplate;
        this.springConfigUsersVaultSecretStore = springConfigUsersVaultSecretStore;
        this.springConfigUsersVaultSecretName = springConfigUsersVaultSecretName;
        this.restTemplate = restTemplate;
        this.springConfigUserRepository = springConfigUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SpringConfigUser> springConfigUserOptional = springConfigUserRepository.findById(
                SpringConfigUserId.builder()
                        .setUsername(username)
                        .build()
        );
        if(springConfigUserOptional.isPresent()) {
            return SpringConfigUserPrincipal.builder()
                    .setSpringConfigUser(springConfigUserOptional.get())
                    .build();
        } else {
            return SpringConfigUserPrincipal.builder()
                    .setSpringConfigUser(
                            SpringConfigUser.builder()
                                    .setUsername("")
                                    .setPassword(this.passwordEncoder.encode(""))
                                    .setEnabled(Boolean.FALSE)
                                    .build()
                    )
                    .build();
        }
    }

    @Override
    public void syncDatabaseUsersWithVault() {
        List<SpringConfigUser> users = new ArrayList<>();
        try {
            String url = this.vaultUrlTemplate
                    .replace(VaultConstants.SECRET_STORE_NAME_PLACEHOLDER, this.springConfigUsersVaultSecretStore)
                    .replace(VaultConstants.SECRET_NAME_PLACEHOLDER, this.springConfigUsersVaultSecretName);
            //Optionally a jwt token can be accepted if vault is secured by an openid provider. Use the restTemplate to fetch the jwt token from an openid provider.
            MultiValueMap<String, String> httpHeaders = new LinkedMultiValueMap<>();
            httpHeaders.add(VaultConstants.VAULT_AUTHENTICATION_HEADER, this.vaultToken);
            logger.info("Polling spring config users from vault. Url: {}", url);
            ResponseEntity<VaultResponse> responseEntity = this.restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(httpHeaders), VaultResponse.class);
            if(responseEntity.getStatusCode().is2xxSuccessful()) {
                VaultResponse vaultResponse = responseEntity.getBody();
                Optional.ofNullable(vaultResponse.getData())
                        .map(Data::getData)
                        .ifPresentOrElse(userMap -> userMap.forEach((username, password) -> users.add(
                                SpringConfigUser.builder()
                                        .setUsername(username)
                                        .setPassword(this.passwordEncoder.encode(password))
                                        .setEnabled(Boolean.TRUE)
                                        .build()
                        )), () -> logger.warn("Failed to map user data from Vault Response. Vault Response Body: {}", vaultResponse));
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
        if(users.size() > 0) {
            springConfigUserRepository.saveAll(users);
            logger.info("Successfully synchronized all users with remote Vault server.");
        } else {
            logger.warn("Remote user secret store is empty.");
        }
    }
}
