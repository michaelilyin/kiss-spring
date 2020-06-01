package ru.hrh.houses.service

import kotlinx.coroutines.flow.Flow
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.hrh.houses.model.house.CurrentHouseListView
import ru.hrh.houses.model.house.HouseCreateInput
import ru.hrh.houses.model.house.HouseUpdateCommonInfoInput
import ru.hrh.houses.model.house.HouseView
import java.util.*

interface HouseService {
  fun getCurrentHousesByUserId(id: UUID): Flux<CurrentHouseListView>
  fun createHouse(input: HouseCreateInput, creatorId: UUID): Mono<HouseView>
  fun getCurrentHousesCountByUserId(id: UUID): Mono<Int>
  fun getHouseInfo(id: String): Mono<HouseView>
  fun deleteHouse(id: String): Mono<String>
  fun updateHouse(input: HouseUpdateCommonInfoInput): Mono<HouseView>
}
