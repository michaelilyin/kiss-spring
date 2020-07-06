package net.kiss.demo.shopping.list.dto

import net.kiss.demo.shopping.list.entity.GoodEntity

data class GoodCreateInput(
  val name: String,
  val description: String?
)

fun GoodCreateInput.toEntity() = GoodEntity(
  id = null,
  name = name,
  description = description
)
