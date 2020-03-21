package net.kiss.demo.goods.model

import net.kiss.service.model.HasName

data class ProductType(
  val id: String,
  val name: String,
  val description: String,
  val category: HasName
)
