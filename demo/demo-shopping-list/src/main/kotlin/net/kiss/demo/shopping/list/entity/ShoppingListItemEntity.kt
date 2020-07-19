package net.kiss.demo.shopping.list.entity

import net.kiss.demo.shopping.list.model.ShoppingListItemState
import net.kiss.demo.shopping.list.model.UnitOfMeasure
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("shopping_list_item")
data class ShoppingListItemEntity(
  @Id
  val id: Long?,
  val goodId: Long,
  val listId: Long,
  var quantity: Int,
  var state: ShoppingListItemState,
  var measure: UnitOfMeasure
)
