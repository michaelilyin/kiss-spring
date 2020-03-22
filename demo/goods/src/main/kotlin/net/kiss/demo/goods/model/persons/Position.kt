package net.kiss.demo.goods.model.persons

import net.kiss.service.model.HasName
import net.kiss.service.model.ID

data class Position(
  override val id: ID,
  override val name: String,
  val description: String
): HasName
