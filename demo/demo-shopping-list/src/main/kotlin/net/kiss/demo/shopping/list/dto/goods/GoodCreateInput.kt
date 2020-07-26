package net.kiss.demo.shopping.list.dto.goods

import net.kiss.demo.shopping.list.entity.GoodEntity
import java.util.*

data class GoodCreateInput(
  val name: String,
  val description: String?,
  val image: String?
)

fun GoodCreateInput.toEntity() = GoodEntity(
  id = null,
  name = name,
  description = description,
  image = if (image == null) null else UUID.fromString(image)
)
