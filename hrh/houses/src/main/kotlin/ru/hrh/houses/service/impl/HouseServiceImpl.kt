package ru.hrh.houses.service.impl

import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import net.kiss.auth.model.longValue
import net.kiss.service.model.page.Page
import net.kiss.service.model.page.newPage
import net.kiss.starter.r2dbc.TxHelper
import net.kiss.starter.service.utils.awaitList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
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
  private val houseUserRepository: HouseUserRepository,
  private val txHelper: TxHelper
) : HouseService {


  override suspend fun getCurrentHousesByUserId(id: UUID): Page<CurrentHouseView> {
    return txHelper.wrap {
      val list = houseRepository.findAllByUserId(id)

      newPage(list.awaitList()) { it.toCurrentView() }
    }.awaitFirst()
  }

  override suspend fun getHouseInfo(id: String): HouseView {
    return txHelper.wrap {
      val house = houseRepository.findById(id.toLong())

      house.awaitFirst().toView()
    }.awaitFirst()
  }

  override suspend fun getCurrentHousesCountByUserId(id: UUID): Int {
    return txHelper.wrap {
      houseRepository.countAllByUserId(id).awaitFirst()
    }.awaitFirst()
  }

  override suspend fun createHouse(input: HouseCreateInput, creatorId: UUID): HouseView {
    return txHelper.wrap {
      val entity = input.toEntity(creatorId)

      val saved = houseRepository.save(entity).awaitFirst()

      val userHouseLink = saved.createUserLink(creatorId, creatorId)
      houseUserRepository.save(userHouseLink).awaitFirst()

      saved.toView()
    }.awaitFirst()
  }

  override suspend fun updateHouse(input: HouseUpdateCommonInfoInput): HouseView {
    return txHelper.wrap {
      val entity = houseRepository.findById(input.id.longValue()).awaitFirst()
      input.fillEntity(entity)

      val saved = houseRepository.save(entity)

      saved.awaitFirst().toView()
    }.awaitFirst()
  }

  override suspend fun deleteHouse(id: String): String {
    return txHelper.wrap {
      val usersDelete = houseUserRepository.deleteAllByHouseId(id.toLong())
      val houseDelete = houseRepository.deleteById(id.toLong())

      usersDelete.awaitFirstOrNull()
      houseDelete.awaitFirstOrNull()

      id
    }.awaitFirst()
  }
}
