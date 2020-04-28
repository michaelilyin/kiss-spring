package ru.hrh.houses.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table("house_users")
data class HouseUserEntity(
  @Id
  val id: Long?,

  val userId: UUID,

  val houseId: Long,

  val attachedBy: UUID,

  val attachedAt: LocalDateTime
)
