package net.kiss.demo.goods.model.persons

import net.kiss.service.model.HasName
import net.kiss.service.model.ID

data class Specialization(
  override val id: ID,
  override val name: String,
  val description: String,
  val logo: String
): HasName
