package net.kiss.demo.shopping.list.dto.shopping.items

import net.kiss.demo.shopping.list.dto.goods.GoodView
import net.kiss.demo.shopping.list.entity.ShoppingListItemEntity
import net.kiss.demo.shopping.list.model.ShoppingListItemState
import net.kiss.demo.shopping.list.model.UnitOfMeasure
import java.lang.IllegalArgumentException

data class ShoppingListItemView(
  val id: String,
  val good: GoodView,
  val quantity: Int,
  val measure: UnitOfMeasure,
  val state: ShoppingListItemState
)

fun ShoppingListItemEntity.toView(goodView: GoodView): ShoppingListItemView {
  if (goodView.id != goodId.toString()) {
    throw IllegalArgumentException("goodId")
  }

  return ShoppingListItemView(
    id = id.toString(),
    good = goodView,
    state = state,
    quantity = quantity,
    measure = measure
  )
}
