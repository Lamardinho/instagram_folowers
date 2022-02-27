package com.example.instagramfollowers.config

import com.example.instagramfollowers.util.AppConstants
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
    @ConfigurationProperties(AppConstants.APP_DATASOURCE)
    fun appDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    @Primary
    @ConfigurationProperties(AppConstants.APP_DATASOURCE_CONFIGURATION)
    fun appDataSource(): DataSource {
        return appDataSourceProperties().initializeDataSourceBuilder().build()
    }
}
