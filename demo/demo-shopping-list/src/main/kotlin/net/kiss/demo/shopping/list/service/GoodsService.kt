package net.kiss.demo.shopping.list.service

import net.kiss.demo.shopping.list.dto.GoodCreateInput
import net.kiss.demo.shopping.list.dto.GoodUpdateInput
import net.kiss.demo.shopping.list.dto.GoodView
import net.kiss.service.model.page.Page
import reactor.core.publisher.Mono

interface GoodsService {
  fun filterGoods(search: String): Mono<Page<GoodView>>
  fun getGoods(offset: Int, limit: Int): Mono<Page<GoodView>>
  fun getGood(id: String): Mono<GoodView>
  fun createGood(input: GoodCreateInput): Mono<GoodView>
  fun updateGood(input: GoodUpdateInput): Mono<GoodView>
}
