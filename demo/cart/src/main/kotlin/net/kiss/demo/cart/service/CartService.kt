package net.kiss.demo.cart.service

import net.kiss.demo.cart.model.Cart

interface CartService {
  fun findCartById(id: Long): Cart?
  fun findCartByUserId(userId: Long): Cart?
  fun createCart(id: Long): Cart
  fun getCartsByIds(ids: List<Long>): List<Cart>
}
