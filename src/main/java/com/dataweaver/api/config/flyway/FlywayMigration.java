package com.dataweaver.api.config.flyway;

import com.dataweaver.api.service.FlywayService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayMigration {

    @Autowired
    FlywayService flywayService;

    @PostConstruct
    public void migrate() {
        flywayService.doMigrations();
    }

}