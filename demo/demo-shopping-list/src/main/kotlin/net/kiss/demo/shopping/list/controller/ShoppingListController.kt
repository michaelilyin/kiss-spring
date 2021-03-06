package net.kiss.demo.shopping.list.controller

import net.kiss.auth.model.CurrentUser
import net.kiss.demo.shopping.list.dto.shopping.lists.ShoppingListCreateInput
import net.kiss.demo.shopping.list.dto.shopping.lists.ShoppingListUpdateInput
import net.kiss.demo.shopping.list.dto.shopping.lists.ShoppingListView
import net.kiss.demo.shopping.list.repository.impl.UserRepositoryImpl
import net.kiss.demo.shopping.list.service.ShoppingListService
import net.kiss.service.model.page.Page
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/shopping-lists")
class ShoppingListController @Autowired() constructor(
  private val shoppingListService: ShoppingListService
) {
  @GetMapping
  fun getMyShoppingLists(
    @RequestParam("search") search: String?,
    @RequestParam("offset", defaultValue = "0") offset: Int,
    @RequestParam("limit", defaultValue = "25") limit: Int,
    currentUser: CurrentUser
  ): Mono<Page<ShoppingListView>> {
    val userId = if (currentUser.authenticated) currentUser.info.id else UserRepositoryImpl.DEFAULT_ID
    return shoppingListService.getUserShoppingLists(userId, search, offset, limit)
  }

  @GetMapping("/{id}")
  fun getShoppingList(@PathVariable("id") id: String): Mono<ShoppingListView> {
    return shoppingListService.findShoppingList(id)
  }

  @PostMapping()
  fun createShoppingList(@RequestBody input: ShoppingListCreateInput, currentUser: CurrentUser): Mono<ShoppingListView> {
    val userId = if (currentUser.authenticated) currentUser.info.id else UserRepositoryImpl.DEFAULT_ID
    return shoppingListService.createShoppingList(input, userId)
  }

  @PutMapping("/{id}")
  fun updateShoppingList(
    @PathVariable("id") id: String,
    @RequestBody input: ShoppingListUpdateInput
  ): Mono<ShoppingListView> {
    return shoppingListService.updateShoppingList(id, input)
  }

  @PutMapping("/{id}/archive")
  fun archiveShoppingList(@PathVariable("id") id: String): Mono<Void> {
    return shoppingListService.archive(id)
  }

  @PutMapping("/{id}/restore")
  fun restoreShoppingList(@PathVariable("id") id: String): Mono<Void> {
    return shoppingListService.restore(id)
  }
}
