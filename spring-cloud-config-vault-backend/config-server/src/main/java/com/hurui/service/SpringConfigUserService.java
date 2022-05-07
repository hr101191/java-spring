package com.hurui.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface SpringConfigUserService extends UserDetailsService {
    void syncDatabaseUsersWithVault();
}
