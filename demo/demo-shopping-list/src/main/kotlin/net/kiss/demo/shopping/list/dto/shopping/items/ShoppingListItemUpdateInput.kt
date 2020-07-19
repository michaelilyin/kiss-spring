package net.kiss.demo.shopping.list.dto.shopping.items

import net.kiss.demo.shopping.list.entity.ShoppingListItemEntity
import net.kiss.demo.shopping.list.model.UnitOfMeasure
import java.lang.IllegalArgumentException

data class ShoppingListItemUpdateInput(
  val unitOfMeasure: UnitOfMeasure,
  val quantity: Int
)

fun ShoppingListItemUpdateInput.fillEntity(entity: ShoppingListItemEntity) {
  entity.measure = unitOfMeasure
  entity.quantity = quantity
}
