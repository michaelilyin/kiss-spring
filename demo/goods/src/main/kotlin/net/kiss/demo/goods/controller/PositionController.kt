package net.kiss.demo.goods.controller

import net.kiss.demo.goods.POSITIONS
import net.kiss.demo.goods.model.persons.Position
import net.kiss.service.model.lists.ListValue
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/positions")
class PositionController {
  @GetMapping()
  suspend fun getAllPositions(): ListValue<Position> {
    return ListValue(POSITIONS)
  }
}
