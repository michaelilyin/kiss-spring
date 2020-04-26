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

fun HouseEntity.createUserLink(userId: String, granterId: String) = HouseUserEntity(
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
  override suspend fun getCurrentHousesByUserId(id: String): Page<CurrentHouseView> {
    val list = houseRepository.findAll()
      .map { it.toCurrentView() }
      .collectList()
      .awaitFirst()
    return Page(list)
  }

  @Transactional
  override suspend fun createHouse(input: HouseCreateInput, creatorId: String): HouseView {
    val entity = input.toEntity(creatorId)

    val saved = houseRepository.save(entity).awaitFirst()
    val userHouseLink = saved.createUserLink(creatorId, creatorId)

    houseUserRepository.save(userHouseLink)
    return saved.toView()
  }
}
