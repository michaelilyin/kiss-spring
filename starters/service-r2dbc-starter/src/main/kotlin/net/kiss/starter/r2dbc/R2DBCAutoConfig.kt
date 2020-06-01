package net.kiss.starter.r2dbc

import io.r2dbc.spi.ConnectionFactory
import net.kiss.starter.r2dbc.converters.EnumWritingConverter
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.data.convert.CustomConversions
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.DialectResolver
import org.springframework.jdbc.datasource.SimpleDriverDataSource
import javax.sql.DataSource

@Configuration
@Import(LiquibaseAutoConfiguration::class)
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

  @Bean
  @LiquibaseDataSource
  fun liquibaseDataSource(dataSourceProperties: LiquibaseProperties): DataSource {
    val url = dataSourceProperties.url
    val user = dataSourceProperties.user
    val password = dataSourceProperties.password
    return DataSourceBuilder.create()
      .type(determineDataSourceType())
      .url(url)
      .username(user)
      .password(password)
      .build()
  }

  @Bean
  fun txHelper() = TxHelper()

  private fun determineDataSourceType(): Class<out DataSource> {
    val type = DataSourceBuilder.findType(null)
    return type ?: SimpleDriverDataSource::class.java
  }
}
