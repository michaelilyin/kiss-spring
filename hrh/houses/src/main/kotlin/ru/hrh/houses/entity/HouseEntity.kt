package ru.hrh.houses.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("houses")
data class HouseEntity(
  @Id
  val id: Long?,

  val name: String,

  val createdBy: String,

  val createdAt: LocalDateTime
)
