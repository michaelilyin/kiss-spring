package ru.hrh.houses.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table("houses")
data class HouseEntity(
  @Id
  val id: Long?,

  var name: String,

  var description: String?,

  val createdBy: UUID,

  val ownedBy: UUID,

  val createdAt: LocalDateTime
)
