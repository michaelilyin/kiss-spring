package net.kiss.demo.cart.repository

import net.kiss.demo.cart.entity.CartEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

@Repository
interface CartRepository : ReactiveMongoRepository<CartEntity, String> {
  fun findByUserId(userId: String): Mono<CartEntity>
}
