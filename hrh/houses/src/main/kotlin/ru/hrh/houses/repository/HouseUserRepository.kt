package ru.hrh.houses.repository

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import ru.hrh.houses.entity.HouseUserEntity
import java.util.*

@Repository
interface HouseUserRepository : ReactiveCrudRepository<HouseUserEntity, Long> {
  // language=PostgreSQL
  @Query("""
    SELECT count(*) <> 0 FROM house_users WHERE user_id = :user_id AND house_id = :house_id
  """)
  fun isMemberOf(@Param("user_id") userId: UUID, @Param("house_id") houseId: Long): Mono<Boolean>
}
