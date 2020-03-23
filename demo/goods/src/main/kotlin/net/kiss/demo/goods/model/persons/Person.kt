package net.kiss.demo.goods.model.persons

import net.kiss.service.model.HasName
import net.kiss.service.model.ID
import java.time.LocalDate

data class Person(
  val id: ID,
  val photo: String,
  val firstName: String,
  val lastName: String?,
  val birthday: LocalDate?,
  val gender: Gender,
  val position: HasName?
)
