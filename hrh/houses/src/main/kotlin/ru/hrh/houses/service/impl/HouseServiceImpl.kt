package ru.hrh.houses.service.impl

import net.kiss.auth.model.longValue
import net.kiss.service.interop.bifunction
import net.kiss.service.model.page.ItemPageInfo
import net.kiss.starter.r2dbc.TxHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.zip
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
  override fun getCurrentHousesByUserId(id: UUID): Flux<CurrentHouseListView> {
    val countMono = houseRepository.countAllByUserId(id).map { ItemPageInfo(it) }
    return houseRepository.findAllByUserId(id)
      .withLatestFrom(countMono, bifunction { house, count ->
        house.toCurrentListView(count)
      })
  }

  @Transactional
  override fun getCurrentHousesCountByUserId(id: UUID): Mono<Int> {
    return houseRepository.countAllByUserId(id)
  }

  @Transactional
  override fun getHouseInfo(id: String): Mono<HouseView> {
    return houseRepository.findById(id.toLong())
      .map { it.toView() }
  }

  @Transactional
  override fun createHouse(input: HouseCreateInput, creatorId: UUID): Mono<HouseView> {
    val entity = input.toEntity(creatorId)

    return houseRepository.save(entity)
      .flatMap { saved ->
        val userHouseLink = saved.createUserLink(creatorId, creatorId)
        houseUserRepository.save(userHouseLink).thenReturn(saved)
      }.map { it.toView() }
  }

  @Transactional
  override fun updateHouse(input: HouseUpdateCommonInfoInput): Mono<HouseView> {
    return houseRepository.findById(input.id.longValue())
      .flatMap { entity ->
        input.fillEntity(entity)
        houseRepository.save(entity)
      }.map { it.toView() }
  }

  @Transactional
  override fun deleteHouse(id: String): Mono<String> {
    return zip(
      houseUserRepository.deleteAllByHouseId(id.toLong()),
      houseRepository.deleteById(id.toLong())
    ) { id }
  }
}
