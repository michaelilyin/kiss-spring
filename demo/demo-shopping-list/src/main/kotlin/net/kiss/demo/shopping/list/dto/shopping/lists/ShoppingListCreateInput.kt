package net.kiss.demo.shopping.list.dto.shopping.lists

import net.kiss.demo.shopping.list.entity.ShoppingListEntity
import java.time.LocalDateTime
import java.util.*

data class ShoppingListCreateInput(
  val name: String,
  val description: String?
)

fun ShoppingListCreateInput.toEntity(userId: UUID, createdTime: LocalDateTime) = ShoppingListEntity(
  id = null,
  name = name,
  description = description,
  createdBy = userId,
  createdAt = createdTime,
  archived = false
)
