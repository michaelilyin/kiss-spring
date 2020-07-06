package net.kiss.demo.shopping.list.repository

import net.kiss.demo.shopping.list.entity.GoodEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface GoodsRepository : ReactiveCrudRepository<GoodEntity, Long> {
  // language=PostgreSQL
  @Query("""
    SELECT * FROM goods WHERE name ILIKE '%' || :name || '%'
  """)
  fun findGoodByName(@Param("name") name: String): Flux<GoodEntity>

  // language=PostgreSQL
  @Query("""
    SELECT * FROM goods OFFSET :offset LIMIT :limit
  """)
  fun findAll(@Param("offset") offset: Int, @Param("limit") limit: Int): Flux<GoodEntity>

  // language=PostgreSQL
  @Query("""
    SELECT count(*) FROM goods
  """)
  fun countAll(): Mono<Int>
}
