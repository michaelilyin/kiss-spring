package ru.hrh.houses.service.impl

import kotlinx.coroutines.reactive.awaitFirst
import net.kiss.service.model.page.Page
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hrh.houses.entity.HouseEntity
import ru.hrh.houses.entity.HouseUserEntity
import ru.hrh.houses.model.*
import ru.hrh.houses.repository.HouseRepository
import ru.hrh.houses.repository.HouseUserRepository
import ru.hrh.houses.service.HouseService
import java.time.LocalDateTime
import java.util.*

fun HouseEntity.createUserLink(userId: UUID, granterId: UUID) = HouseUserEntity(
  id = null,
  userId = userId,
  houseId = id!!,
  attachedAt = LocalDateTime.now(),
  attachedBy = granterId
)

@Service
class HouseServiceImpl @Autowired constructor(
  private val houseRepository: HouseRepository,
  private val houseUserRepository: HouseUserRepository
) : HouseService {
  @Transactional
  override suspend fun getCurrentHousesByUserId(id: UUID): Page<CurrentHouseView> {
    val list = houseRepository.findAllByUserId(id)
      .map { it.toCurrentView() }
      .collectList()
      .awaitFirst()
    return Page(list)
  }

  @Transactional
  override suspend fun createHouse(input: HouseCreateInput, creatorId: UUID): HouseView {
    val entity = input.toEntity(creatorId)

    val saved = houseRepository.save(entity).awaitFirst()
    val userHouseLink = saved.createUserLink(creatorId, creatorId)

    houseUserRepository.save(userHouseLink).awaitFirst()
    return saved.toView()
  }
}
