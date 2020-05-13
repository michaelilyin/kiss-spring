package ru.hrh.houses.service.impl

import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import net.kiss.auth.model.longValue
import net.kiss.service.model.page.Page
import net.kiss.service.model.page.newPage
import net.kiss.starter.service.utils.awaitList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hrh.houses.entity.HouseEntity
import ru.hrh.houses.entity.HouseUserEntity
import ru.hrh.houses.model.house.*
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

    return newPage(list.awaitList()) { it.toCurrentView() }
  }

  @Transactional
  override suspend fun getHouseInfo(id: String): HouseView {
    val house = houseRepository.findById(id.toLong())

    return house.awaitFirst().toView()
  }

  @Transactional
  override suspend fun getCurrentHousesCountByUserId(id: UUID): Int {
    return houseRepository.countAllByUserId(id).awaitFirst()
  }

  @Transactional
  override suspend fun createHouse(input: HouseCreateInput, creatorId: UUID): HouseView {
    val entity = input.toEntity(creatorId)

    val saved = houseRepository.save(entity).awaitFirst()

    val userHouseLink = saved.createUserLink(creatorId, creatorId)
    houseUserRepository.save(userHouseLink).awaitFirst()

    return saved.toView()
  }

  @Transactional
  override suspend fun updateHouse(input: HouseUpdateCommonInfoInput): HouseView {
    val entity = houseRepository.findById(input.id.longValue()).awaitFirst()
    input.fillEntity(entity)

    val saved = houseRepository.save(entity)

    return saved.awaitFirst().toView()
  }

  @Transactional
  override suspend fun deleteHouse(id: String): String {
    val usersDelete = houseUserRepository.deleteAllByHouseId(id.toLong())
    val houseDelete = houseRepository.deleteById(id.toLong())

    usersDelete.awaitFirstOrNull()
    houseDelete.awaitFirstOrNull()

    return id
  }
}
