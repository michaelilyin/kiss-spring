package net.kiss.demo.goods.model

import net.kiss.service.model.SimpleNamedItem

data class Category(
  val id: String,
  val name: String,
  val description: String,
  val image: String,
  val parent: SimpleNamedItem
)
