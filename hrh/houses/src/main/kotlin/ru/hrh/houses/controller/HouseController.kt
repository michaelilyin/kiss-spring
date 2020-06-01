package ru.hrh.houses.controller

import net.kiss.auth.model.CurrentUser
import net.kiss.service.model.Value
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.hrh.houses.model.house.CurrentHouseListView
import ru.hrh.houses.model.house.HouseCreateInput
import ru.hrh.houses.model.house.HouseUpdateCommonInfoInput
import ru.hrh.houses.model.house.HouseView
import ru.hrh.houses.service.HouseService
import java.time.Duration
import java.util.*
import java.util.function.Function

@RestController
@RequestMapping("/api")
class HouseController @Autowired constructor(
  private val houseService: HouseService
) {

  @PreAuthorize("hasAnyRole(@roles.houseMember)")
  @GetMapping(produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
  fun getUserCurrentHouses(currentUser: CurrentUser): Flux<CurrentHouseListView> {
    return houseService.getCurrentHousesByUserId(currentUser.info.id)
  }

  @PreAuthorize("hasAnyRole(@roles.houseMember)")
  @GetMapping("/count")
  fun getUserCurrentHousesCount(currentUser: CurrentUser): Mono<Value<Int>> {
    return houseService.getCurrentHousesCountByUserId(currentUser.info.id).map { Value(it) }
  }

  @PreAuthorize("""
    hasAnyRole(@roles.houseMember) && hasPermission(#id, @housePermissions.type, @housePermissions.read)
  """)
  @GetMapping("/{id}")
  fun getHouseInfo(@PathVariable("id") id: String): Mono<HouseView> {
    return houseService.getHouseInfo(id)
  }

  @PreAuthorize("hasRole(@roles.houseMember) && !hasRole(@roles.demo)")
  @PostMapping
  fun postHouse(@RequestBody input: HouseCreateInput, currentUser: CurrentUser): Mono<HouseView> {
    return houseService.createHouse(input, currentUser.info.id)
  }

  @PreAuthorize("""
    hasRole(@roles.houseMember) && !hasRole(@roles.demo)
      && hasPermission(#id, @housePermissions.type, @housePermissions.update)
  """)
  @PutMapping("/{id}")
  fun putHouse(
    @PathVariable("id") id: String,
    @RequestBody input: HouseUpdateCommonInfoInput
  ): Mono<HouseView> {
    if (id != input.id) {
      throw IllegalArgumentException()
    }
    return houseService.updateHouse(input)
  }

  @PreAuthorize("""
    hasRole(@roles.houseMember) && !hasRole(@roles.demo)
      && hasPermission(#id, @housePermissions.type, @housePermissions.delete)
  """)
  @DeleteMapping("/{id}")
  fun deleteHouse(@PathVariable("id") id: String): Mono<Value<String>> {
    return houseService.deleteHouse(id).map { Value(it) }
  }
}
