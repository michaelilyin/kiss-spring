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
  val photo: String,
  val firstName: String,
  val lastName: String?,
//  @JsonSerialize(using = LocalDateSerializer::class)
  val birthday: String?,
  val gender: Gender,
  val position: HasName?
)
