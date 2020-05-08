package ru.hrh.houses.service

import net.kiss.service.model.page.Page
import ru.hrh.houses.model.house.CurrentHouseView
import ru.hrh.houses.model.house.HouseCreateInput
import ru.hrh.houses.model.house.HouseUpdateCommonInfoInput
import ru.hrh.houses.model.house.HouseView
import java.util.*

interface HouseService {
  suspend fun getCurrentHousesByUserId(id: UUID): Page<CurrentHouseView>
  suspend fun createHouse(input: HouseCreateInput, creatorId: UUID): HouseView
  suspend fun getCurrentHousesCountByUserId(id: UUID): Int
  suspend fun getHouseInfo(id: String): HouseView
  suspend fun deleteHouse(id: String): String
  suspend fun updateHouse(input: HouseUpdateCommonInfoInput): HouseView
}
