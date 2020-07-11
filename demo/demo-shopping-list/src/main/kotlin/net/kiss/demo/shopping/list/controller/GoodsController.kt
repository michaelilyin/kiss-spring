package net.kiss.demo.shopping.list.controller

import net.kiss.demo.shopping.list.dto.goods.GoodCreateInput
import net.kiss.demo.shopping.list.dto.goods.GoodUpdateInput
import net.kiss.demo.shopping.list.dto.goods.GoodView
import net.kiss.demo.shopping.list.service.GoodsService
import net.kiss.service.model.page.Page
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.lang.IllegalArgumentException

@RestController
@RequestMapping("/api/goods")
class GoodsController @Autowired constructor(
  private val goodsService: GoodsService
) {

  @GetMapping("/by-name")
  fun getGoods(@RequestParam(name = "search", required = true) search: String): Mono<Page<GoodView>> {
    return goodsService.filterGoods(search);
  }

  @GetMapping()
  fun getGoods(
    @RequestParam("offset", defaultValue = "0") offset: Int,
    @RequestParam("limit", defaultValue = "25") limit: Int
  ): Mono<Page<GoodView>> {
    return goodsService.getGoods(offset, limit)
  }

  @GetMapping("/{id}")
  fun getGood(@PathVariable("id") id: String): Mono<GoodView> {
    return goodsService.getGood(id)
  }

  @PostMapping()
  fun createGood(@RequestBody() input: GoodCreateInput): Mono<GoodView> {
    return goodsService.createGood(input)
  }

  @PutMapping("/{id}")
  fun updateGood(@PathVariable("id") id: String, @RequestBody() input: GoodUpdateInput): Mono<GoodView> {
    if (id != input.id) {
      throw IllegalArgumentException("ID")
    }
    return goodsService.updateGood(input)
  }
}
