package ru.hrh.houses.controller

import mu.KotlinLogging
import net.kiss.auth.model.CurrentUser
import net.kiss.service.model.Value
import net.kiss.starter.service.utils.returnMono
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import ru.hrh.houses.model.HouseCreateInput
import ru.hrh.houses.model.HouseUpdateCommonInfoInput
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

  @PreAuthorize("""
    hasAnyRole('house-member', 'house-demo-member') && hasPermission(#id, @housePermissions.type, @housePermissions.read)
  """)
  @GetMapping("/{id}")
  fun getHouseInfo(@PathVariable("id") id: String) = returnMono {
    houseService.getHouseInfo(id)
  }

  @PreAuthorize("hasAnyRole('house-member', 'house-demo-member')")
  @GetMapping("/count")
  fun getUserCurrentHousesCount(currentUser: CurrentUser) = returnMono {
    val count = houseService.getCurrentHousesCountByUserId(currentUser.info.id)
    return@returnMono Value(count)
  }

  @PreAuthorize("hasRole('house-member')")
  @PostMapping
  fun postHouse(@RequestBody input: HouseCreateInput, currentUser: CurrentUser) = returnMono {
    houseService.createHouse(input, currentUser.info.id)
  }

  @PreAuthorize("hasRole('house-member') && hasPermission(#id, @housePermissions.type, @housePermissions.update)")
  @PutMapping("/{id}")
  fun putHouse(@PathVariable("id") id: String, @RequestBody input: HouseUpdateCommonInfoInput) = returnMono {
    if (id != input.id) {
      throw IllegalArgumentException()
    }
    houseService.updateHouse(input)
  }

  @PreAuthorize("hasRole('house-member') && hasPermission(#id, @housePermissions.type, @housePermissions.delete)")
  @DeleteMapping("/{id}")
  fun deleteHouse(@PathVariable("id") id: String) = returnMono {
    val deleted = houseService.deleteHouse(id)
    return@returnMono Value(deleted)
  }
}
