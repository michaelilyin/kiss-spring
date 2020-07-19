package net.kiss.demo.shopping.list.service.impl

import net.kiss.demo.shopping.list.dto.goods.toView
import net.kiss.demo.shopping.list.dto.shopping.items.*
import net.kiss.demo.shopping.list.entity.ShoppingListItemEntity
import net.kiss.demo.shopping.list.model.ShoppingListItemState
import net.kiss.demo.shopping.list.repository.GoodsRepository
import net.kiss.demo.shopping.list.repository.ShoppingListItemRepository
import net.kiss.demo.shopping.list.service.ShoppingListItemsService
import net.kiss.service.model.page.Page
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ShoppingListItemsServiceImpl @Autowired() constructor(
  private val shoppingListItemRepository: ShoppingListItemRepository,
  private val goodsRepository: GoodsRepository
) : ShoppingListItemsService {
  override fun getShoppingListItemsByListId(listId: String, offset: Int, limit: Int): Mono<Page<ShoppingListItemView>> {
    return shoppingListItemRepository.countItemsByListId(listId.toLong())
      .flatMap { count ->
        shoppingListItemRepository.getItemsByListId(listId.toLong(), offset, limit)
          .flatMap { listItemToView(it) }
          .collectList()
          .map { Page(it, count) }
      }
  }

  override fun addItemToList(listId: String, input: ShoppingListItemCreateInput): Mono<ShoppingListItemView> {
    val entity = input.toEntityCreate(listId)
    return shoppingListItemRepository.save(entity)
      .flatMap { listItemToView(it) }
  }

  override fun remoteItemFromList(itemId: String): Mono<Void> {
    return shoppingListItemRepository.deleteById(itemId.toLong())
  }

  override fun updateListItem(itemId: String, input: ShoppingListItemUpdateInput): Mono<ShoppingListItemView> {
    return shoppingListItemRepository.findById(itemId.toLong())
      .flatMap { entity ->
        input.fillEntity(entity)
        return@flatMap shoppingListItemRepository.save(entity)
      }
      .flatMap { listItemToView(it) }
  }

  override fun completeListGood(itemId: String): Mono<ShoppingListItemView> {
    return shoppingListItemRepository.findById(itemId.toLong())
      .flatMap { entity ->
        entity.state = ShoppingListItemState.COMPLETED
        return@flatMap shoppingListItemRepository.save(entity)
      }
      .flatMap { listItemToView(it) }
  }

  override fun excludeListGood(itemId: String): Mono<ShoppingListItemView> {
    return shoppingListItemRepository.findById(itemId.toLong())
      .flatMap { entity ->
        entity.state = ShoppingListItemState.EXCLUDED
        return@flatMap shoppingListItemRepository.save(entity)
      }
      .flatMap { listItemToView(it) }
  }

  private fun listItemToView(listItem: ShoppingListItemEntity) =
    goodsRepository.findById(listItem.goodId)
      .map { listItem.toView(it.toView()) }
}
