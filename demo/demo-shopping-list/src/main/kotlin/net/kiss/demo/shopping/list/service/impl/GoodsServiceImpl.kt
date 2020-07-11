package net.kiss.demo.shopping.list.service.impl

import net.kiss.demo.shopping.list.dto.goods.*
import net.kiss.demo.shopping.list.repository.GoodsRepository
import net.kiss.demo.shopping.list.service.GoodsService
import net.kiss.service.model.page.Page
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
class GoodsServiceImpl @Autowired constructor(
  private val goodsRepository: GoodsRepository
) : GoodsService {
  @Transactional
  override fun filterGoods(search: String): Mono<Page<GoodView>> {
    return goodsRepository.findGoodByName(search)
      .map { it.toView() }
      .collectList()
      .map { Page(it, it.size) }
  }

  @Transactional
  override fun getGoods(offset: Int, limit: Int): Mono<Page<GoodView>> {
    return Mono.zip(
      goodsRepository.findAll(offset, limit).map { it.toView() }.collectList(),
      goodsRepository.countAll()
    ).map { Page(it.t1, it.t2) }
  }

  @Transactional
  override fun updateGood(input: GoodUpdateInput): Mono<GoodView> {
    return goodsRepository.findById(input.id.toLong())
      .flatMap {
        input.fillEntity(it)
        goodsRepository.save(it)
      }
      .map { it.toView() }
  }

  @Transactional
  override fun getGood(id: String): Mono<GoodView> {
    return goodsRepository.findById(id.toLong()).map { it.toView() }
  }

  @Transactional
  override fun createGood(input: GoodCreateInput): Mono<GoodView> {
    return goodsRepository.save(input.toEntity())
      .map { it.toView() }
  }
}
