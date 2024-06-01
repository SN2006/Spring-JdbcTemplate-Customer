package com.example.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@ComponentScan("com.example.app")
@PropertySource("classpath:app.properties")
public class AppContext {

    private final Environment env;

    @Autowired
    public AppContext(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("jdbcDriver")));
        dataSource.setUrl(env.getProperty("dbUrl"));
        dataSource.setUsername(env.getProperty("dbUser"));
        dataSource.setPassword(env.getProperty("dbPass"));
        return dataSource;
    }
}
