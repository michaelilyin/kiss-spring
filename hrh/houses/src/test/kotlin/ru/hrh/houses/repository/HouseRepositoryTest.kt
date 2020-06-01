package ru.hrh.houses.repository

import com.github.javafaker.Faker
import kotlinx.coroutines.runBlocking
import net.kiss.starter.r2dbc.R2DBCAutoConfig
import net.kiss.starter.service.utils.awaitList
import net.kiss.starter.test.containers.PostgresContainer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlConfig
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*

@DataR2dbcTest
@Testcontainers
@Import(R2DBCAutoConfig::class)
@Sql("classpath:init.sql", config = SqlConfig(
  dataSource = "liquibaseDataSource"
))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = [HouseRepositoryTest.Companion.PostgresInitializer::class])
class HouseRepositoryTest @Autowired constructor(
  private val houseRepository: HouseRepository
) {
  private val faker = Faker.instance()

  companion object {
    @Container
    val postgres = PostgresContainer()

    class PostgresInitializer : PostgresContainer.Initializer(postgres)
  }

  @Test
  fun testGetCurrentHouses() = runBlocking {
    val userId = UUID.fromString("71a0f36b-69fb-42ec-9fa8-181a3d80bb9e")
    val list = houseRepository.findAllByUserId(userId).awaitList()

    Assertions.assertEquals(3, list.size)
  }
}
