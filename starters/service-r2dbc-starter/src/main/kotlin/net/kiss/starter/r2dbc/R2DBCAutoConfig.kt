package net.kiss.starter.r2dbc

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.data.relational.core.mapping.NamingStrategy
import org.springframework.data.relational.core.mapping.RelationalMappingContext
import java.util.*

@Configuration
@EnableConfigurationProperties(R2DBCProperties::class)
@EnableJdbcRepositories
class R2DBCAutoConfig @Autowired constructor(
  private val properties: R2DBCProperties
) : AbstractR2dbcConfiguration() {

  @Bean
  override fun connectionFactory(): ConnectionFactory {
    return PostgresqlConnectionFactory(
      PostgresqlConnectionConfiguration
        .builder()
        .host(properties.host)
        .database(properties.database)
        .username(properties.username)
        .password(properties.password)
        .port(properties.port)
        .schema(properties.schema)
        .build()
    );
  }

  @Bean
  @Primary
  override fun r2dbcMappingContext(
    namingStrategy: Optional<NamingStrategy>,
    r2dbcCustomConversions: R2dbcCustomConversions
  ): RelationalMappingContext {
    return super.r2dbcMappingContext(namingStrategy, r2dbcCustomConversions)
  }
}
