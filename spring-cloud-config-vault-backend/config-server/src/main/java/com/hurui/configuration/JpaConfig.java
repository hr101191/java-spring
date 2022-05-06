package com.hurui.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"com.hurui.entity"})
@EnableJpaRepositories(basePackages = {"com.hurui.repository"})
public class JpaConfig {
}
