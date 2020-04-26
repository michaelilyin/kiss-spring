package ru.hrh.houses.model

import ru.hrh.houses.entity.HouseEntity

data class HouseView(
  val id: String,
  val name: String
)

fun HouseEntity.toView() = HouseView(
  id = id.toString(),
  name = name
)
