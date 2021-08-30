package com.example.demo.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
public class QuartzConfiguration {

    @Bean
    @QuartzDataSource
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource quartzDataSource(){
        return DataSourceBuilder.create().build();
    }
}
