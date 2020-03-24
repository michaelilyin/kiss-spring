package net.kiss.demo.goods.model.persons

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import net.kiss.service.model.ID
import java.time.LocalDateTime

data class PersonSpecialization(
  val id: ID,
  val person: String,
  val specialization: SpecializationBrief,
//  @JsonSerialize(using = LocalDateTimeSerializer::class)
  val since: String
)

data class PersonSpecializationCreateInput(
  val specialization: String
)
