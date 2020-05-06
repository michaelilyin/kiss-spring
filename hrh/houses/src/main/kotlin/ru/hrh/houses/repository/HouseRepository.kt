package ru.hrh.houses.repository

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.hrh.houses.entity.HouseEntity
import java.util.*

@Repository
interface HouseRepository : ReactiveCrudRepository<HouseEntity, Long> {
  // language=PostgreSQL
  @Query("""
    SELECT * FROM houses
    WHERE id IN (
        SELECT house_id FROM house_users WHERE user_id = :user_id
    )
    ORDER BY id
  """)
  fun findAllByUserId(@Param("user_id") userId: UUID): Flux<HouseEntity>

  // language=PostgreSQL
  @Query("""
    SELECT count(*) FROM houses
    WHERE id IN (
        SELECT house_id FROM house_users WHERE user_id = :user_id
    )
  """)
  fun countAllByUserId(@Param("user_id") userId: UUID): Mono<Int>

  // language=PostgreSQL
  @Query("""
    SELECT created_by = :user_id FROM houses WHERE id = :house_id
  """)
  fun isOwnerOf(@Param("house_id") houseId: Long, @Param("user_id") userId: UUID): Mono<Boolean>
}
