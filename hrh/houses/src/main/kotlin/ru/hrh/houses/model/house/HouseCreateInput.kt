package ru.hrh.houses.model.house

import ru.hrh.houses.entity.HouseEntity
import java.time.LocalDateTime
import java.util.*

data class HouseCreateInput(
  val name: String,
  val description: String?
)

fun HouseCreateInput.toEntity(creatorId: UUID): HouseEntity = HouseEntity(
  id = null,
  name = name,
  description = description,
  createdBy = creatorId,
  ownedBy = creatorId,
  createdAt = LocalDateTime.now()
)
