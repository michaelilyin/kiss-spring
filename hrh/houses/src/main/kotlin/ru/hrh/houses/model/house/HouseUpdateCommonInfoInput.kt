package ru.hrh.houses.model.house

import ru.hrh.houses.entity.HouseEntity

data class HouseUpdateCommonInfoInput(
  val id: String,
  val name: String,
  val description: String?
)

fun HouseUpdateCommonInfoInput.fillEntity(houseEntity: HouseEntity) {
  if (houseEntity.id != id.toLong()) {
    throw IllegalArgumentException()
  }

  houseEntity.name = name
  houseEntity.description = description
}
