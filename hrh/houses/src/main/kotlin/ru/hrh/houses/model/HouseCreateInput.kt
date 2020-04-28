package ru.hrh.houses.model

import ru.hrh.houses.entity.HouseEntity
import java.time.LocalDateTime
import java.util.*

data class HouseCreateInput(
  val name: String
)

fun HouseCreateInput.toEntity(creatorId: UUID): HouseEntity = HouseEntity(
  id = null,
  name = name,
  createdBy = creatorId,
  createdAt = LocalDateTime.now()
)
