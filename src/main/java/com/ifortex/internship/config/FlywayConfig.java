package com.ifortex.internship.config;

import com.ifortex.internship.config.datasource.ApplicationDatasourceProperties;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

  private final ApplicationDatasourceProperties applicationProperties;

  public FlywayConfig(ApplicationDatasourceProperties applicationProperties) {
    this.applicationProperties = applicationProperties;
  }

  @Bean(initMethod = "migrate")
  public Flyway flyway() {
    Flyway flyway =
        Flyway.configure()
            .failOnMissingLocations(true)
            .baselineOnMigrate(true)
            .dataSource(
                applicationProperties.getUrl(),
                applicationProperties.getUsername(),
                applicationProperties.getPassword())
            .locations("classpath:db/migration")
            .load();

    flyway.info();

    return flyway;
  }
}
