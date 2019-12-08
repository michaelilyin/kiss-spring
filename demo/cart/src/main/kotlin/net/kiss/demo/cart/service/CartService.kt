package net.kiss.demo.cart.service

import net.kiss.demo.cart.model.Cart

interface CartService {
  fun findCartById(id: String): Cart?
  fun findCartByUserId(userId: Long): Cart?
  fun createCartForUser(userId: Long): Cart
  fun getCartsByIds(ids: List<String>): List<Cart>
}
