package ru.hrh.houses.model

import net.kiss.service.model.ID
import ru.hrh.houses.entity.HouseEntity

data class CurrentHouseView(
  val id: ID,
  val name: String
) {
}

fun HouseEntity.toCurrentView() = CurrentHouseView(
  id = id.toString(),
  name = name
)
