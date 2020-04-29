package ru.hrh.houses.service

import net.kiss.service.model.Value
import net.kiss.service.model.page.Page
import ru.hrh.houses.model.CurrentHouseView
import ru.hrh.houses.model.HouseCreateInput
import ru.hrh.houses.model.HouseView
import java.util.*

interface HouseService {
  suspend fun getCurrentHousesByUserId(id: UUID): Page<CurrentHouseView>
  suspend fun createHouse(input: HouseCreateInput, creatorId: UUID): HouseView
  suspend fun getCurrentHousesCountByUserId(id: UUID): Value<Int>
}
