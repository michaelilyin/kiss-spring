package net.kiss.demo.goods.model

import net.kiss.service.model.HasName

data class Product(
  val id: String,
  val name: String,
  val description: String,
  val image: String?,
  val type: HasName
)
