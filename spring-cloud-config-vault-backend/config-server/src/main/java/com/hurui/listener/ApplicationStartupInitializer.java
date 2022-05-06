package com.hurui.listener;

import com.hurui.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupInitializer implements ApplicationListener<ApplicationStartedEvent> {

    private final Logger logger = LoggerFactory.getLogger(ApplicationStartupInitializer.class);

    private final ConfigService configService;

    public ApplicationStartupInitializer(ConfigService configService) {
        this.configService = configService;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        logger.info("Synchronizing database with remote Vault server on application startup.");
        configService.syncDatabaseConfigWithVault();
        logger.info("Successfully synchronized database with remote Vault server on application startup.");
    }
}
