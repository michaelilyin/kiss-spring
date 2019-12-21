package net.kiss.demo.users.repository

import net.kiss.demo.users.helpers.entity.UserEntityHelper
import net.kiss.demo.users.helpers.sql.SqlValues
import net.kiss.starter.service.utils.orNull
import net.kiss.starter.test.containers.PostgresContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@DataJdbcTest
@Testcontainers
@Sql(
  "classpath:sql/clean.sql",
  "classpath:sql/prefill_users.sql"
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = [UserRepositoryTest.Companion.PostgresInitializer::class])
class UserRepositoryTest @Autowired constructor(
  private val userRepository: UserRepository
) {
  companion object {
    @Container
    val postgres = PostgresContainer()

    class PostgresInitializer : PostgresContainer.Initializer(postgres)
  }

  @Test
  fun `should find user by id`() {
    val frodo = userRepository.findByIdOrNull(SqlValues.frodo.id!!)

    assertThat(frodo).isNotNull
    assertThat(frodo?.id).isEqualTo(SqlValues.frodo.id!!)
    assertThat(frodo?.username).isEqualTo(SqlValues.frodo.username)
  }

  @Test
  fun `should find user by username`() {
    val frodo = userRepository.findUserByUsername(SqlValues.frodo.username)

    assertThat(frodo).isNotNull
    assertThat(frodo?.id).isEqualTo(SqlValues.frodo.id!!)
    assertThat(frodo?.username).isEqualTo(SqlValues.frodo.username)
  }

  @Test
  fun `should create new user`() {
    val user = UserEntityHelper.userEntity(id = null)

    val saved = userRepository.save(user)
    val res = userRepository.findByIdOrNull(saved.id!!)

    assertThat(res).isNotNull
    assertThat(res?.id).isEqualTo(saved.id!!)
    assertThat(res?.username).isEqualTo(user.username)
  }

  @Test
  fun `should return all users`() {
    val users = userRepository.findAll().toList()

    assertThat(users.size).isEqualTo(4)
    assertThat(users).extracting("username")
      .containsExactly(
        SqlValues.frodo.username,
        SqlValues.sam.username,
        SqlValues.aragorn.username,
        SqlValues.gandalf.username
      )
  }
}
