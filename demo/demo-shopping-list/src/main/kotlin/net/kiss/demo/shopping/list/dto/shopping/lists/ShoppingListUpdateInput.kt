package net.kiss.demo.shopping.list.dto.shopping.lists

import net.kiss.demo.shopping.list.entity.ShoppingListEntity

data class ShoppingListUpdateInput(
  val name: String,
  val description: String?
)

fun ShoppingListUpdateInput.fillEntity(entity: ShoppingListEntity) {
  entity.name = name
  entity.description = description
}
