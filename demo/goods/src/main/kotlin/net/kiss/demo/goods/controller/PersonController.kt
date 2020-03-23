package net.kiss.demo.goods.controller

import net.kiss.demo.goods.PERSONS
import net.kiss.demo.goods.PERSON_SPECS
import net.kiss.demo.goods.model.persons.Person
import net.kiss.demo.goods.model.persons.PersonSpecialization
import net.kiss.service.exception.NotFoundException
import net.kiss.service.model.lists.ListValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/persons")
class PersonController {
  @GetMapping
  fun getPersons(): ListValue<Person> {
    return ListValue(PERSONS)
  }

  @GetMapping("/{id}")
  fun getPerson(@PathVariable("id") id: String): Person {
    return PERSONS.find { it.id == id } ?: throw NotFoundException("person")
  }

  @GetMapping("/{id}/specializations")
  fun getPersonSpecializations(@PathVariable("id") id: String): ListValue<PersonSpecialization> {
    return ListValue(PERSON_SPECS[id] ?: emptyList())
  }
}
