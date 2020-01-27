package net.kiss.demo.cart.service.impl

import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import mu.KotlinLogging
import net.kiss.demo.cart.entity.CartEntity
import net.kiss.demo.cart.model.Cart
import net.kiss.demo.cart.model.CartCreateInput
import net.kiss.demo.cart.model.toEntity
import net.kiss.demo.cart.model.toModel
import net.kiss.demo.cart.repository.CartRepository
import net.kiss.demo.cart.service.CartService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CartServiceImpl @Autowired constructor(
  private val cartRepository: CartRepository
) : CartService {
  private val logger = KotlinLogging.logger {}

  override suspend fun findCartById(id: String): Cart? {
    val cart = cartRepository.findById(id).awaitFirstOrNull()
    return cart?.toModel()
  }

  override suspend fun findCartByUserId(userId: String): Cart? {
    val cart = cartRepository.findByUserId(userId).awaitFirstOrNull()
    return cart?.toModel()
  }

  override suspend fun createCartForUser(cart: CartCreateInput): Cart {
    val entity = cart.toEntity()

    val saved = cartRepository.save(entity).awaitFirst()

    return saved.toModel()
  }

  override suspend fun getCartsByIds(ids: List<String>): List<Cart> {
    val carts = cartRepository.findAllById(ids)
      .buffer()
      .awaitFirst()

    return carts.map { it.toModel() }
  }
}
