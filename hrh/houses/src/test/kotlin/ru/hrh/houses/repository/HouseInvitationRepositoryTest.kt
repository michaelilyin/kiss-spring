package ru.hrh.houses.repository

import com.github.javafaker.Faker
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
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
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import ru.hrh.houses.entity.HouseInvitationEntity
import ru.hrh.houses.model.invitation.InvitationResolution
import java.time.LocalDateTime
import java.util.*

@DataR2dbcTest
@Testcontainers
@Import(R2DBCAutoConfig::class)
@Sql("classpath:init.sql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = [HouseInvitationRepositoryTest.Companion.PostgresInitializer::class])
class HouseInvitationRepositoryTest @Autowired constructor(
  private val houseInvitationRepository: HouseInvitationRepository
) {
  private val faker = Faker.instance()

  companion object {
    @Container
    val postgres = PostgresContainer()

    class PostgresInitializer : PostgresContainer.Initializer(postgres)
  }

  @Test
  fun createInvitation() = runBlocking {
    val entity = HouseInvitationEntity(
      id = null,
      houseId = 1,
      userEmail = faker.internet().emailAddress(),
      invitedBy = UUID.randomUUID(),
      invitedAt = LocalDateTime.now(),
      invitation = faker.lorem().paragraph(),
      resolutionStatus = InvitationResolution.NEW,
      resolution = null,
      resolvedBy = null,
      resolvedAt = null
    )

    val saved = houseInvitationRepository.save(entity).awaitFirst()

    Assertions.assertNotNull(saved?.id)
  }

  @Test
  fun getHouseInvitations() = runBlocking {
    val invitationsPage = houseInvitationRepository.getHouseInvitations(
      houseId = 1,
      active = true,
      rejected = false,
      accepted = false,
      cancelled = false,
      offset = 0,
      limit = 10,
      order = "created_at desc"
    ).awaitList()

    val invitationsPageCount = houseInvitationRepository.getHouseInvitationsCount(
      houseId = 1,
      active = true,
      rejected = false,
      accepted = false,
      cancelled = false
    ).awaitFirst()

    Assertions.assertEquals(2, invitationsPage.size)
    Assertions.assertEquals(2, invitationsPageCount)
  }

  @Test
  fun getHouseInvitationByEmail() = runBlocking {
    val invitationsPage = houseInvitationRepository.getHouseInvitationsByUserEmail(
      email = "john@hrh.ru",
      active = true,
      accepted = false,
      cancelled = false,
      rejected = false
    ).awaitList()

    Assertions.assertEquals(1, invitationsPage.size)
    Assertions.assertEquals("john@hrh.ru", invitationsPage.first().userEmail)
  }

  @Test
  fun updateResolutionStatus() = runBlocking {
    val user = UUID.randomUUID()
    val resolution = faker.lorem().paragraph()
    val status = InvitationResolution.ACCEPTED

    houseInvitationRepository.updateResolution(
      2, status.name, resolution, user, LocalDateTime.now()
    ).awaitFirstOrNull()
    val updated = houseInvitationRepository.findById(2).awaitFirst()

    Assertions.assertEquals(status, updated.resolutionStatus)
    Assertions.assertEquals("jim@hrh.ru", updated.userEmail)
  }
}
