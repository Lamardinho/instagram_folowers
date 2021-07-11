package com.example.instagramfollowers.config

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.sql.DataSource

@Configuration
class DataSourceConfig {
    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.instagramfollowers")
    fun weblibraryDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.instagramfollowers.configuration")
    fun weblibraryDataSource(): DataSource {
        return weblibraryDataSourceProperties().initializeDataSourceBuilder().build()
    }
}
