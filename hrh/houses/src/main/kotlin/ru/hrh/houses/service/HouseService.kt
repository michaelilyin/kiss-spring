package ru.hrh.houses.service

import net.kiss.service.model.page.Page
import ru.hrh.houses.model.CurrentHouseView
import ru.hrh.houses.model.HouseCreateInput
import ru.hrh.houses.model.HouseView

interface HouseService {
  suspend fun getCurrentHousesByUserId(id: String): Page<CurrentHouseView>
  suspend fun createHouse(input: HouseCreateInput, creatorId: String): HouseView
}
