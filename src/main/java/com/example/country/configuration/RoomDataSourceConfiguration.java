package com.example.country.configuration;

import com.example.country.models.Country;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.country.repository.country",
        entityManagerFactoryRef = "countryEntityManagerFactory",
        transactionManagerRef= "countryTransactionManager")
public class RoomDataSourceConfiguration {

    @Bean
    @ConfigurationProperties("app.datasource.country")
    public DataSourceProperties countryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource.country.configuration")
    public DataSource countryDataSource() {
        return countryDataSourceProperties().initializeDataSourceBuilder()
                .type(BasicDataSource.class).build();
    }

    @Bean(name = "countryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean countryEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(countryDataSource())
                .packages(Country.class)
                .build();
    }

    @Bean
    public PlatformTransactionManager countryTransactionManager(
            final @Qualifier("countryEntityManagerFactory") LocalContainerEntityManagerFactoryBean countryEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(countryEntityManagerFactory.getObject()));
    }

}