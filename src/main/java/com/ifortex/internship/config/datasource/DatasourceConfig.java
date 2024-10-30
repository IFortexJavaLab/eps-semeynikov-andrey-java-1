package com.ifortex.internship.config.datasource;

import com.zaxxer.hikari.HikariDataSource;

public interface DatasourceConfig {
  HikariDataSource dataSource();
}
