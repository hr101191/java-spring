package com.hurui.service;

import com.hurui.repository.ConfigRepository;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigService {

    private final ConfigRepository configRepository;

    public ConfigServiceImpl(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }
}
