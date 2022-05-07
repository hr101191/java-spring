package com.hurui.listener;

import com.hurui.service.ConfigService;
import com.hurui.service.SpringConfigUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupInitializer implements ApplicationListener<ApplicationStartedEvent> {

    private final Logger logger = LoggerFactory.getLogger(ApplicationStartupInitializer.class);

    private final ConfigService configService;
    private final SpringConfigUserService springConfigUserService;

    public ApplicationStartupInitializer(ConfigService configService, SpringConfigUserService springConfigUserService) {
        this.configService = configService;
        this.springConfigUserService = springConfigUserService;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        logger.info("Synchronizing database with remote Vault server on application startup.");
        this.configService.syncDatabaseConfigWithVault();
        this.springConfigUserService.syncDatabaseUsersWithVault();
        logger.info("Successfully synchronized database with remote Vault server on application startup.");
    }
}
