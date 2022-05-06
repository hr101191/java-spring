package com.hurui.repository;

import com.hurui.entity.Config;
import com.hurui.entity.ConfigId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<Config, ConfigId> {
}
