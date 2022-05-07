package com.hurui.configuration;

import com.hurui.service.ConfigService;
import com.hurui.service.SpringConfigUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerConfig.class);

    private final ConfigService configService;
    private final SpringConfigUserService springConfigUserService;

    public SchedulerConfig(ConfigService configService, SpringConfigUserService springConfigUserService) {
        this.configService = configService;
        this.springConfigUserService = springConfigUserService;
    }

    @Scheduled(initialDelay = 60000L, fixedRate = 300000L)
    public void syncDatabaseConfigWithVaultTask() {
        logger.info("Executing scheduled job to sync database config with vault.");
        this.configService.syncDatabaseConfigWithVault();
    }

    @Scheduled(initialDelay = 60000L, fixedRate = 300000L)
    public void syncDatabaseUsersWithVaultTask() {
        logger.info("Executing scheduled job to sync database users with vault.");
        this.springConfigUserService.syncDatabaseUsersWithVault();
    }
}
