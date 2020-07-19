package net.kiss.demo.shopping.list.service.impl

import net.kiss.demo.shopping.list.dto.*
import net.kiss.demo.shopping.list.dto.shopping.lists.*
import net.kiss.demo.shopping.list.entity.ShoppingListEntity
import net.kiss.demo.shopping.list.repository.ShoppingListRepository
import net.kiss.demo.shopping.list.repository.UserRepository
import net.kiss.demo.shopping.list.service.ShoppingListService
import net.kiss.service.model.page.Page
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import java.time.LocalDateTime
import java.util.*

@Service
class ShoppingListServiceImpl(
  private val shoppingListRepository: ShoppingListRepository,
  private val userRepository: UserRepository
) : ShoppingListService {
  override fun getUserShoppingLists(userId: UUID, offset: Int, limit: Int): Mono<Page<ShoppingListView>> {
    return shoppingListRepository.countListsByUserId(userId)
      .flatMap { count ->
        shoppingListRepository.getListsByUserId(userId, offset, limit)
          .flatMap {
            mapShoppingListToView(it)
          }
          .collectList()
          .map { Page(it, count) }
      }
  }

  override fun findShoppingList(id: String): Mono<ShoppingListView> {
    return shoppingListRepository.findById(id.toLong()).flatMap {
      mapShoppingListToView(it)
    }
  }

  override fun createShoppingList(input: ShoppingListCreateInput, userId: UUID): Mono<ShoppingListView> {
    val entity = input.toEntity(userId, LocalDateTime.now())
    return shoppingListRepository.save(entity)
      .flatMap {
        mapShoppingListToView(it)
      }
  }

  override fun updateShoppingList(id: String, input: ShoppingListUpdateInput): Mono<ShoppingListView> {
    return shoppingListRepository.findById(id.toLong())
      .flatMap {
        input.fillEntity(it)
        shoppingListRepository.save(it)
      }
      .flatMap {
        mapShoppingListToView(it)
      }
  }

  override fun archive(id: String): Mono<Void> {
    return shoppingListRepository.archive(id.toLong()).then()
  }

  override fun restore(id: String): Mono<Void> {
    return shoppingListRepository.restore(id.toLong()).then()
  }

  private fun mapShoppingListToView(list: ShoppingListEntity) =
    userRepository.findById(list.createdBy).map { list.toView(it.toView()) }
}
