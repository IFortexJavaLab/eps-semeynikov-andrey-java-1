package com.ifortex.internship.config;

import com.zaxxer.hikari.HikariDataSource;
import java.util.Objects;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.ifortex.internship")
@EnableTransactionManagement
@EnableScheduling
public class AppConfig implements WebMvcConfigurer {

  @Bean
  public PropertySourcesPlaceholderConfigurer propertyConfigurer() {
    YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
    yamlFactory.setResources(new ClassPathResource("application.yaml"));

    PropertySourcesPlaceholderConfigurer propertiesConfigurer =
        new PropertySourcesPlaceholderConfigurer();
    propertiesConfigurer.setProperties(Objects.requireNonNull(yamlFactory.getObject()));
    return propertiesConfigurer;
  }

  @Bean
  public LocalValidatorFactoryBean validator() {
    return new LocalValidatorFactoryBean();
  }

  @Bean
  public DataSourceTransactionManager transactionManager(HikariDataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }
}
