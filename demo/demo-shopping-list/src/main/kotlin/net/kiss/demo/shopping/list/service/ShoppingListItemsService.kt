package net.kiss.demo.shopping.list.service

import net.kiss.demo.shopping.list.dto.shopping.items.ShoppingListItemCreateInput
import net.kiss.demo.shopping.list.dto.shopping.items.ShoppingListItemUpdateInput
import net.kiss.demo.shopping.list.dto.shopping.items.ShoppingListItemView
import net.kiss.service.model.page.Page
import reactor.core.publisher.Mono

interface ShoppingListItemsService {
  fun getShoppingListItemsByListId(listId: String, offset: Int, limit: Int): Mono<Page<ShoppingListItemView>>
  fun addItemToList(listId: String, input: ShoppingListItemCreateInput): Mono<ShoppingListItemView>
  fun remoteItemFromList(itemId: String): Mono<Void>
  fun updateListItem(itemId: String, input: ShoppingListItemUpdateInput): Mono<ShoppingListItemView>
  fun completeListGood(itemId: String): Mono<ShoppingListItemView>
  fun excludeListGood(itemId: String): Mono<ShoppingListItemView>
}
