package net.kiss.demo.cart.service

import net.kiss.demo.cart.model.Cart
import net.kiss.demo.cart.model.CartCreateInput

interface CartService {
  suspend fun findCartById(id: String): Cart?
  suspend fun findCartByUserId(userId: String): Cart?
  suspend fun createCartForUser(cart: CartCreateInput): Cart
  suspend fun getCartsByIds(ids: List<String>): List<Cart>
}
