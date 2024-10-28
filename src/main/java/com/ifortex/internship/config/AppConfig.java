package com.ifortex.internship.config;

import java.util.Objects;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.ifortex.internship")
public class AppConfig implements WebMvcConfigurer {

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
    YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
    yamlFactory.setResources(new ClassPathResource("application.yaml"));

    PropertySourcesPlaceholderConfigurer propertiesConfigurer =
        new PropertySourcesPlaceholderConfigurer();
    propertiesConfigurer.setProperties(Objects.requireNonNull(yamlFactory.getObject()));
    return propertiesConfigurer;
  }
}
