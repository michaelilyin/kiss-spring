package net.kiss.demo.shopping.list.entity

import net.kiss.demo.shopping.list.model.ShoppingListItemState
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("shopping_list_item")
data class ShoppingListItemEntity(
  @Id
  val id: Long?,
  val goodId: Long,
  val listId: Long,
  val quantity: Int,
  val state: ShoppingListItemState
)
