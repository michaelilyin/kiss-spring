package net.kiss.demo.cart.model

import net.kiss.demo.cart.entity.CartEntity

data class Cart(
  val id: String,
  val userId: Long
) {
}

fun CartEntity.toModel() = Cart(
  id = id!!,
  userId = userId
)
