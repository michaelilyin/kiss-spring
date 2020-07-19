package net.kiss.demo.shopping.list.controller

import net.kiss.demo.shopping.list.dto.shopping.items.ShoppingListItemCreateInput
import net.kiss.demo.shopping.list.dto.shopping.items.ShoppingListItemUpdateInput
import net.kiss.demo.shopping.list.dto.shopping.items.ShoppingListItemView
import net.kiss.demo.shopping.list.service.ShoppingListItemsService
import net.kiss.service.model.page.Page
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/shopping-lists/{listId}")
class ShoppingListItemsController @Autowired constructor(
  private val shoppingListItemsService: ShoppingListItemsService
) {

  @GetMapping("/items")
  fun getListGoods(
    @PathVariable("listId") listId: String,
    @RequestParam("offset", defaultValue = "0") offset: Int,
    @RequestParam("limit", defaultValue = "25") limit: Int
  ): Mono<Page<ShoppingListItemView>> {
    return shoppingListItemsService.getShoppingListItemsByListId(listId, offset, limit)
  }

  @PostMapping("/items")
  fun addListGood(
    @PathVariable("listId") listId: String,
    @RequestBody input: ShoppingListItemCreateInput
  ): Mono<ShoppingListItemView> {
    return shoppingListItemsService.addItemToList(listId, input)
  }

  @DeleteMapping("/items/{itemId}")
  fun removeListGood(
    @PathVariable("listId") listId: String,
    @PathVariable("itemId") itemId: String
  ): Mono<Void> {
    return shoppingListItemsService.remoteItemFromList(itemId)
  }

  @PutMapping("/items/{itemId}")
  fun editListGood(
    @PathVariable("listId") listId: String,
    @PathVariable("itemId") itemId: String,
    @RequestBody input: ShoppingListItemUpdateInput
  ): Mono<ShoppingListItemView> {
    return shoppingListItemsService.updateListItem(itemId, input)
  }

  @PutMapping("/items/{itemId}/complete")
  fun completeListGood(
    @PathVariable("listId") listId: String,
    @PathVariable("itemId") itemId: String
  ): Mono<ShoppingListItemView> {
    return shoppingListItemsService.completeListGood(itemId)
  }

  @PutMapping("/items/{itemId}/exclude")
  fun excludeListGood(
    @PathVariable("listId") listId: String,
    @PathVariable("itemId") itemId: String
  ): Mono<ShoppingListItemView> {
    return shoppingListItemsService.excludeListGood(itemId)
  }
}
