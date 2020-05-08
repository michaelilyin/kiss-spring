package ru.hrh.houses.model.house

import ru.hrh.houses.entity.HouseEntity
import java.util.*

data class HouseView(
  val id: String,
  val name: String,
  val description: String?,
  val ownerBy: UUID
)

fun HouseEntity.toView() = HouseView(
        id = id.toString(),
        name = name,
        description = description,
        ownerBy = ownedBy
)
