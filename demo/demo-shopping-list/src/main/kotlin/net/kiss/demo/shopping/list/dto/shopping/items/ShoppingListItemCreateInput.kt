package net.kiss.demo.shopping.list.dto.shopping.items

import net.kiss.demo.shopping.list.entity.ShoppingListEntity
import net.kiss.demo.shopping.list.entity.ShoppingListItemEntity
import net.kiss.demo.shopping.list.model.ShoppingListItemState
import net.kiss.demo.shopping.list.model.UnitOfMeasure
import java.time.LocalDateTime

data class ShoppingListItemCreateInput(
  val goodId: String,
  val unitOfMeasure: UnitOfMeasure,
  val quantity: Int
)

fun ShoppingListItemCreateInput.toEntityCreate(listId: String) = ShoppingListItemEntity(
  id = null,
  listId = listId.toLong(),
  goodId = goodId.toLong(),
  measure = unitOfMeasure,
  quantity = quantity,
  state = ShoppingListItemState.ACTIVE
)
