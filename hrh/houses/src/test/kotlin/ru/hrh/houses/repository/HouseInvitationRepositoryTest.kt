package ru.hrh.houses.repository

import com.github.javafaker.Faker
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.runBlocking
import net.kiss.service.model.page.PageRequest
import net.kiss.service.model.sort.SortRequest
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
import ru.hrh.houses.entity.HouseInvitationEntity
import ru.hrh.houses.entity.update.ResolveInvitationEntityUpdate
import ru.hrh.houses.model.invitation.InvitationResolution
import java.time.LocalDateTime
import java.util.*

@DataR2dbcTest
@Testcontainers
@Import(R2DBCAutoConfig::class)
@Sql("classpath:init.sql", config = SqlConfig(
  dataSource = "liquibaseDataSource"
))
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
      statuses = setOf(InvitationResolution.NEW),
      email = null,
      page = PageRequest(0, 10),
      sort = SortRequest("id")
    ).awaitList()

    val invitationsPageCount = houseInvitationRepository.getHouseInvitationsCount(
      houseId = 1,
      statuses = setOf(InvitationResolution.NEW),
      email = null
    ).awaitFirst()

    Assertions.assertEquals(2, invitationsPage.size)
    Assertions.assertEquals(2, invitationsPageCount)
  }

  @Test
  fun getHouseInvitationByEmail() = runBlocking {
    val invitationsPage = houseInvitationRepository.getHouseInvitations(
      houseId = null,
      statuses = setOf(InvitationResolution.NEW),
      email = "john@hrh.ru",
      page = PageRequest(0, 10),
      sort = SortRequest("id")
    ).awaitList()

    Assertions.assertEquals(1, invitationsPage.size)
    Assertions.assertEquals("john@hrh.ru", invitationsPage.first().userEmail)
  }

  @Test
  fun updateResolutionStatus() = runBlocking {
    val user = UUID.randomUUID()
    val resolution = faker.lorem().paragraph()
    val status = InvitationResolution.ACCEPTED
    val update = ResolveInvitationEntityUpdate(
      status, resolution, user, LocalDateTime.now()
    )

    houseInvitationRepository.updateResolution(2, update).awaitFirstOrNull()
    val updated = houseInvitationRepository.findById(2).awaitFirst()

    Assertions.assertEquals(status, updated.resolutionStatus)
    Assertions.assertEquals("jim@hrh.ru", updated.userEmail)
  }
}
