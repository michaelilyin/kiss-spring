package net.kiss.demo.goods.controller

import net.kiss.demo.goods.SPECIALIZATIONS
import net.kiss.demo.goods.model.persons.Specialization
import net.kiss.demo.goods.model.persons.SpecializationBrief
import net.kiss.demo.goods.model.persons.toBrief
import net.kiss.service.exception.NotFoundException
import net.kiss.service.model.page.Page
import net.kiss.service.model.page.newPage
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/specializations")
class SpecializationController {
  @GetMapping
  suspend fun getAllSpecializations(): Page<SpecializationBrief> {
    return newPage(SPECIALIZATIONS.map { it.toBrief() })
  }

  @GetMapping("/{id}")
  suspend fun getSpecialization(@PathVariable("id") id: String): Specialization {
    return SPECIALIZATIONS.find { it.id == id } ?: throw NotFoundException("specialization")
  }
}
