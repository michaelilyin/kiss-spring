package net.kiss.demo.goods.model.persons

import net.kiss.service.model.HasName
import net.kiss.service.model.ID
import java.time.LocalDateTime

data class PersonSpecialization(
  val id: ID,
  val person: String,
  val specialization: SpecializationBrief,
  val since: LocalDateTime
)
