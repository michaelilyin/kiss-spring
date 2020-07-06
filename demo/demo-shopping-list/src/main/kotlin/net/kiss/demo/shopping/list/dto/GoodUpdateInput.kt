package net.kiss.demo.shopping.list.dto

import net.kiss.demo.shopping.list.entity.GoodEntity

data class GoodUpdateInput(
  val id: String,
  val name: String,
  val description: String?
)

fun GoodUpdateInput.fillEntity(entity: GoodEntity) {
  if (entity.id?.toString() != id) {
    throw IllegalArgumentException("ID")
  }
  entity.name = name;
  entity.description = description;
}
