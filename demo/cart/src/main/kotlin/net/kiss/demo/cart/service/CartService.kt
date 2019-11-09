package net.kiss.demo.cart.service

import net.kiss.demo.cart.model.Cart

interface CartService {
  fun findCartById(id: Long): Cart?
  fun findCartByUserId(userId: Long): Cart?
}
