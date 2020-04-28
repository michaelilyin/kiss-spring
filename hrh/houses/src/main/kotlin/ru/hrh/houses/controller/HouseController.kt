package ru.hrh.houses.controller

import mu.KotlinLogging
import net.kiss.auth.model.CurrentUser
import net.kiss.starter.service.utils.returnMono
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import ru.hrh.houses.model.HouseCreateInput
import ru.hrh.houses.service.HouseService
import java.lang.IllegalStateException

@RestController
@RequestMapping("/api")
class HouseController @Autowired constructor(
  private val houseService: HouseService
) {

  @PreAuthorize("hasAnyRole('house-member', 'house-demo-member')")
  @GetMapping
  fun getUserCurrentHouses(currentUser: CurrentUser) = returnMono {
    houseService.getCurrentHousesByUserId(currentUser.info.id)
  }

  @PreAuthorize("hasRole('house-member')")
  @PostMapping
  fun postHouse(@RequestBody input: HouseCreateInput, currentUser: CurrentUser) = returnMono {
    houseService.createHouse(input, currentUser.info.id)
  }
}
