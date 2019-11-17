package net.kiss.starter.test.containers

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer

class PostgresContainer(
  database: String = "test",
  username: String = "test",
  password: String = "test"
) : PostgreSQLContainer<PostgresContainer>("postgres:11.1") {
  init {
    withDatabaseName(database)
    withUsername(username)
    withPassword(password)
  }

  open class Initializer(
    private val postgres: PostgresContainer
  ) : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
      TestPropertyValues.of(
        "spring.datasource.url=${postgres.jdbcUrl}",
        "spring.datasource.username=${postgres.username}",
        "spring.datasource.password=${postgres.password}"
      ).applyTo(applicationContext.environment)
    }
  }
}
