package net.kiss.demo.shopping.list.repository

import net.kiss.demo.shopping.list.entity.ShoppingListItemEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface ShoppingListItemRepository : ReactiveCrudRepository<ShoppingListItemEntity, Long> {
  // language=PostgreSQL
  @Query("""
    SELECT * FROM shopping_list_item
    WHERE list_id = :listId
    OFFSET :offset
    LIMIT :limit
  """)
  fun getItemsByListId(
    @Param("listId") listId: Long,
    @Param("offset") offset: Int,
    @Param("limit") limit: Int
  ): Flux<ShoppingListItemEntity>

  // language=PostgreSQL
  @Query("""
    SELECT count(*) FROM shopping_list_item
    WHERE list_id = :listId
  """)
  fun countItemsByListId(
    @Param("listId") listId: Long
  ): Mono<Int>
}
