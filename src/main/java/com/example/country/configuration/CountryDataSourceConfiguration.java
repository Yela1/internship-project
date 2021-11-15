package com.example.country.configuration;

import com.example.country.models.Room;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.example.country.repository.room",
                        entityManagerFactoryRef = "roomEntityManagerFactory",
                        transactionManagerRef = "roomTransactionManager")
public class CountryDataSourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.room")
    public DataSourceProperties roomDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.room.configuration")
    public DataSource roomDataSource() {
        return roomDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "roomEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean roomEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(roomDataSource())
                .packages(Room.class)
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager roomTransactionManager(
            final @Qualifier("roomEntityManagerFactory") LocalContainerEntityManagerFactoryBean roomEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(roomEntityManagerFactory.getObject()));
    }
}
