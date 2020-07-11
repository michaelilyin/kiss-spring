package net.kiss.demo.shopping.list.dto.shopping.lists

import net.kiss.demo.shopping.list.dto.UserView
import net.kiss.demo.shopping.list.entity.ShoppingListEntity
import java.time.LocalDateTime

data class ShoppingListView (
  val id: String,
  val name: String,
  val createdAt: LocalDateTime,
  val createdBy: UserView,
  val description: String?,
  val archived: Boolean
)

fun ShoppingListEntity.toView(user: UserView): ShoppingListView {
  if (user.id != createdBy) {
    throw IllegalArgumentException("user")
  }

  return ShoppingListView(
    id = id!!.toString(),
    name = name,
    createdAt = createdAt,
    description = description,
    createdBy = user,
    archived = archived
  )
}
