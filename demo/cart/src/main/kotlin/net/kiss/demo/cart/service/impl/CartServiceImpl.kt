package net.kiss.demo.cart.service.impl

import mu.KotlinLogging
import net.kiss.demo.cart.model.Cart
import net.kiss.demo.cart.service.CartService
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

@Service
class CartServiceImpl : CartService {
  private val logger = KotlinLogging.logger {}

  private final val ID = AtomicLong(0)

  private final val CARTS = mutableListOf(
    Cart(ID.incrementAndGet(), 1),
    Cart(ID.incrementAndGet(), 2),
    Cart(ID.incrementAndGet(), 3)
  )

  private val BY_ID = CARTS.groupBy { it.id }
  override fun findCartById(id: Long): Cart? {
    return BY_ID[id]?.first()
  }

  override fun findCartByUserId(userId: Long): Cart? {
    return CARTS.first { it.userId == userId }
  }

  override fun createCart(id: Long): Cart {
    val cart = Cart(ID.incrementAndGet(), id)
    CARTS.add(cart)
    return cart
  }
}
