package net.kiss.demo.shopping.list.dto.goods

import net.kiss.demo.shopping.list.entity.GoodEntity
import java.util.*

data class GoodUpdateInput(
  val id: String,
  val name: String,
  val description: String?,
  val image: String?
)

fun GoodUpdateInput.fillEntity(entity: GoodEntity) {
  if (entity.id?.toString() != id) {
    throw IllegalArgumentException("ID")
  }
  entity.name = name;
  entity.description = description;
  entity.image = if (image == null) null else UUID.fromString(image)
}
