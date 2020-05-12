package net.kiss.starter.r2dbc

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(DataSourceAutoConfiguration::class, LiquibaseAutoConfiguration::class)
class R2DBCAutoConfig {
}
