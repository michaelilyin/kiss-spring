package net.kiss.demo.shopping.list.dto

import net.kiss.demo.shopping.list.entity.GoodEntity

data class GoodView(
  val id: String,
  val name: String,
  val description: String?
) {
}

fun GoodEntity.toView() = GoodView(
  id = id!!.toString(),
  name = name,
  description = description
)
