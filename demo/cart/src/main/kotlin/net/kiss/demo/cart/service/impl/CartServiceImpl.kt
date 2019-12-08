package net.kiss.demo.cart.service.impl

import mu.KotlinLogging
import net.kiss.demo.cart.entity.CartEntity
import net.kiss.demo.cart.model.Cart
import net.kiss.demo.cart.model.toModel
import net.kiss.demo.cart.repository.CartRepository
import net.kiss.demo.cart.service.CartService
import net.kiss.starter.service.utils.orNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

@Service
class CartServiceImpl @Autowired constructor(
  private val cartRepository: CartRepository
) : CartService {
  private val logger = KotlinLogging.logger {}

  override fun findCartById(id: String): Cart? {
    val cart = cartRepository.findById(id).orNull()
    return cart?.toModel()
  }

  override fun findCartByUserId(userId: Long): Cart? {
    val cart = cartRepository.findByUserId(userId).orNull()
    return cart?.toModel()
  }

  override fun createCartForUser(userId: Long): Cart {
    val cart = CartEntity(
      id = null,
      userId = userId
    )

    val saved = cartRepository.save(cart)

    return saved.toModel()
  }

  override fun getCartsByIds(ids: List<String>): List<Cart> {
    val carts = cartRepository.findAllById(ids)

    return carts.map { it.toModel() }
  }
}
