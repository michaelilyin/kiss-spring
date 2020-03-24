package net.kiss.demo.goods.model.persons

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import net.kiss.service.model.HasName
import net.kiss.service.model.ID
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class Person(
  val id: ID,
  var photo: String,
  var firstName: String,
  var lastName: String?,
//  @JsonSerialize(using = LocalDateSerializer::class)
  var birthday: String?,
  var gender: Gender,
  var position: HasName?
)

data class PersonCreateInput(
  val photo: String,
  val firstName: String,
  val lastName: String?,
  val birthday: String?,
  val gender: Gender,
  val positionId: String?
)

data class PersonUpdateInput(
  val photo: String,
  val firstName: String,
  val lastName: String?,
  val birthday: String?,
  val gender: Gender,
  val positionId: String?
)
