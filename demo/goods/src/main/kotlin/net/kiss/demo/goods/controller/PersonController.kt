package net.kiss.demo.goods.controller

import net.kiss.demo.goods.*
import net.kiss.demo.goods.model.persons.*
import net.kiss.service.exception.NotFoundException
import net.kiss.service.model.lists.ListValue
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/api/persons")
class PersonController {
  @GetMapping
  suspend fun getPersons(): ListValue<Person> {
    return ListValue(PERSONS)
  }

  @GetMapping("/{id}")
  suspend fun getPerson(@PathVariable("id") id: String): Person {
    return PERSONS.find { it.id == id } ?: throw NotFoundException("person")
  }

  @PostMapping
  suspend fun createPerson(@RequestBody input: PersonCreateInput): Person {
    val person = Person(
      id = id(),
      firstName = input.firstName,
      lastName = input.lastName,
      photo = input.photo,
      gender = input.gender,
      birthday = input.birthday,
      position = input.positionId?.let { findPosition(it) }
    )

    PERSONS.add(person)

    return person;
  }

  @PutMapping("/{id}")
  suspend fun updatePerson(@PathVariable("id") id: String, @RequestBody input: PersonUpdateInput): Person {
    val person = PERSONS.find { it.id == id } ?: throw NotFoundException("person")

    person.apply {
      firstName = input.firstName
      lastName = input.lastName
      birthday = input.birthday
      gender = input.gender
      position = input.positionId?.let { findPosition(it) }
    }

    return person
  }

  @GetMapping("/{id}/specializations")
  suspend fun getPersonSpecializations(@PathVariable("id") id: String): ListValue<PersonSpecialization> {
    return ListValue(PERSON_SPECS[id] ?: emptyList())
  }

  @PostMapping("/{id}/specializations")
  suspend fun addSpecialization(@PathVariable("id") id: String, @RequestBody input: PersonSpecializationCreateInput): PersonSpecialization {
    val spec = PersonSpecialization(
      id = id(),
      person = id,
      specialization = findSpecialization(input.specialization).toBrief(),
      since = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
    )

    val specs = PERSON_SPECS.computeIfAbsent(id) { mutableListOf() }

    val existing = specs.find { it.specialization.id == input.specialization }
    if (existing != null) {
      return existing
    }

    specs.add(spec)

    return spec
  }

  @DeleteMapping("/{id}/specializations/{spec-id}")
  suspend fun deleteSpecialization(@PathVariable("id") id: String, @PathVariable("spec-id") specId: String): String {
    val specs = PERSON_SPECS[id] ?: throw NotFoundException("person-spec")
    specs.removeIf { it.id == specId }
    return specId
  }

  private fun findPosition(positionId: String) =
    POSITIONS.find { it.id == positionId } ?: throw NotFoundException("position")

  private fun findSpecialization(specId: String) =
    SPECIALIZATIONS.find { it.id == specId } ?: throw NotFoundException("spec")
}
