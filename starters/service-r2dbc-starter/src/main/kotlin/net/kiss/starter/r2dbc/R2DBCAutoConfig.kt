package net.kiss.starter.r2dbc

import io.r2dbc.spi.ConnectionFactory
import net.kiss.starter.r2dbc.converters.EnumWritingConverter
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.data.convert.CustomConversions
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.DialectResolver
import java.util.*

@Configuration
@Import(DataSourceAutoConfiguration::class, LiquibaseAutoConfiguration::class)
class R2DBCAutoConfig {
  @Bean
  fun r2dbcCustomConversions(connectionFactory: ConnectionFactory): R2dbcCustomConversions {
    val dialect = DialectResolver.getDialect(connectionFactory)
    val converters = dialect.converters.toMutableList() + R2dbcCustomConversions.STORE_CONVERTERS
    return R2dbcCustomConversions(
      CustomConversions.StoreConversions.of(dialect.simpleTypeHolder, converters),
      listOf(
        EnumWritingConverter()
      )
    )
  }
}
