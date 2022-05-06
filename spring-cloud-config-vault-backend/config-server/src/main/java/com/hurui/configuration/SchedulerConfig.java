package com.hurui.configuration;

import com.hurui.service.ConfigService;
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

    public SchedulerConfig(ConfigService configService) {
        this.configService = configService;
    }

    @Scheduled(initialDelay = 60000L, fixedRate = 300000L)
    public void test() {
        logger.info("Executing scheduled job to sync database config with vault.");
        this.configService.syncDatabaseConfigWithVault();
    }
}
