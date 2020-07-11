package net.kiss.demo.shopping.list.service

import net.kiss.demo.shopping.list.dto.shopping.lists.ShoppingListCreateInput
import net.kiss.demo.shopping.list.dto.shopping.lists.ShoppingListUpdateInput
import net.kiss.demo.shopping.list.dto.shopping.lists.ShoppingListView
import net.kiss.service.model.page.Page
import reactor.core.publisher.Mono
import java.util.*

interface ShoppingListService {
  fun getUserShoppingLists(userId: UUID, offset: Int, limit: Int): Mono<Page<ShoppingListView>>
  fun findShoppingList(id: String): Mono<ShoppingListView>
  fun createShoppingList(input: ShoppingListCreateInput, userId: UUID): Mono<ShoppingListView>
  fun updateShoppingList(id: String, input: ShoppingListUpdateInput): Mono<ShoppingListView>
  fun archive(id: String): Mono<Void>
  fun restore(id: String): Mono<Void>
}
