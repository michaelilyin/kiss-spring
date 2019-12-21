package net.kiss.demo.cart.repository

import net.kiss.demo.cart.entity.CartEntity
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface CartRepository : MongoRepository<CartEntity, String> {
  fun findByUserId(userId: Long): CartEntity?
}
