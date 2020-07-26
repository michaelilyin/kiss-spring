package net.kiss.demo.shopping.list.repository

import net.kiss.demo.shopping.list.entity.ShoppingListEntity
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Repository
interface ShoppingListRepository : ReactiveCrudRepository<ShoppingListEntity, Long> {
  // language=PostgreSQL
  @Query("""
    SELECT * FROM shopping_lists WHERE archived IS FALSE AND created_by = :userId AND (:search IS NULL OR name ilike '%' || :search || '%' ) OFFSET :offset LIMIT :limit
  """)
  fun getListsByUserId(
    @Param("userId") userId: UUID,
    @Param("search") search: String?,
    @Param("offset") offset: Int,
    @Param("limit") limit: Int
  ): Flux<ShoppingListEntity>

  // language=PostgreSQL
  @Query("""
    SELECT count(*) FROM shopping_lists WHERE archived IS FALSE AND created_by = :userId AND (:search IS NULL OR name ilike '%' || :search || '%' )
  """)
  fun countListsByUserId(
    @Param("userId") userId: UUID,
    @Param("search") search: String?
  ): Mono<Int>

  // language=PostgreSQL
  @Query("""
    UPDATE shopping_lists SET archived = TRUE WHERE id = :id
  """)
  @Modifying
  fun archive(@Param("id") id: Long): Mono<Int>

  // language=PostgreSQL
  @Query("""
    UPDATE shopping_lists SET archived = FALSE WHERE id = :id
  """)
  @Modifying
  fun restore(@Param("id") id: Long): Mono<Int>
}
