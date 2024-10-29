package com.ifortex.internship.config.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DevDatasourceConfig implements DatasourceConfig {

  private final ApplicationDatasourceProperties applicationProperties;

  public DevDatasourceConfig(ApplicationDatasourceProperties applicationProperties) {
    this.applicationProperties = applicationProperties;
  }

  @Override
  @Bean
  public HikariDataSource dataSource() {
    HikariConfig hikariConfig = new HikariConfig();

    hikariConfig.setDriverClassName(applicationProperties.getDriver());
    hikariConfig.setJdbcUrl(applicationProperties.getUrl());
    hikariConfig.setUsername(applicationProperties.getUsername());
    hikariConfig.setPassword(applicationProperties.getPassword());

    hikariConfig.setPoolName("devSpringHikariCP");
    hikariConfig.setAutoCommit(true);
    hikariConfig.setMaximumPoolSize(20);

    return new HikariDataSource(hikariConfig);
  }

  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource());
  }
}
