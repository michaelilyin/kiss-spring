package net.kiss.demo.cart.service.impl

import mu.KotlinLogging
import net.kiss.demo.cart.model.Cart
import net.kiss.demo.cart.service.CartService
import org.springframework.stereotype.Service

@Service
class CartServiceImpl : CartService {
  private val logger = KotlinLogging.logger {}

  private val CARTS = listOf(
    Cart(1, 1),
    Cart(2, 2),
    Cart(3, 3)
  )

  private val BY_ID = CARTS.groupBy { it.id }
  override fun findCartById(id: Long): Cart? {
    return BY_ID[id]?.first()
  }

  override fun findCartByUserId(userId: Long): Cart? {
    return CARTS.first { it.userId == userId }
  }
}
