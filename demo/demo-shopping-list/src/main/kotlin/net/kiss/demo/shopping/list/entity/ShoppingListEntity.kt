package net.kiss.demo.shopping.list.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table("shopping_lists")
data class ShoppingListEntity(
  @Id
  val id: Long?,
  var name: String,
  val createdAt: LocalDateTime,
  val createdBy: UUID,
  var description: String?,
  var archived: Boolean
)
