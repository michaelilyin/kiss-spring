package net.kiss.demo.goods.model.persons

import net.kiss.service.model.HasName
import net.kiss.service.model.ID

class SpecializationBrief(
  override val id: ID,
  override val name: String,
  val logo: String
) : HasName

fun Specialization.toBrief(): SpecializationBrief {
  return SpecializationBrief(
    id = this.id,
    name = this.name,
    logo = this.logo
  )
}
