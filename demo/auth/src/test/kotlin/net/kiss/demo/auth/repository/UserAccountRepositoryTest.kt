package net.kiss.demo.auth.repository

import net.kiss.starter.service.utils.orNull
import net.kiss.starter.test.containers.PostgresContainer
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@DataJdbcTest
@Testcontainers
@Sql(
  "classpath:sql/clean.sql"
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = [UserAccountRepositoryTest.Companion.PostgresInitializer::class])
class UserAccountRepositoryTest @Autowired constructor(
  private val userAccountRepository: UserAccountRepository
)  {
  companion object {
    @Container
    val postgres = PostgresContainer()

    class PostgresInitializer : PostgresContainer.Initializer(postgres)
  }

  @Test
  fun `should find user by username`() {
    val admin = userAccountRepository.findByUsername("admin")

    assertThat(admin).isNotNull
    assertThat(admin?.username).isEqualTo("admin")
    assertThat(admin?.enabled).isTrue()
    assertThat(admin?.roles).isNotEmpty
  }
}
